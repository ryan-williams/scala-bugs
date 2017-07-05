package org.abc

import java.io.{ EOFException, IOException }
import java.nio.ByteBuffer
import java.nio.file.Paths
import java.util

import org.abc.CachingChannel.Config

import scala.math.{ max, min }

case class CachingChannel[Channel <: SeekableByteChannel](channel: Channel)(
    implicit config: Config
)
  extends SeekableByteChannel {

  val Config(blockSize, maxReadAttempts, maximumSize) = config
  val maxNumBlocks = math.ceil(maximumSize.toDouble / blockSize).toInt

  private val _buffer = ByteBuffer.allocate(blockSize)

  val blocks =
    new util.LinkedHashMap[Long, ByteBuffer](
      (maximumSize / blockSize).toInt,
      0.7f,
      true
    ) {
      override def removeEldestEntry(eldest: util.Map.Entry[Long, ByteBuffer]): Boolean =
        size() > maxNumBlocks
    }

  def getBlock(idx: Long): ByteBuffer =
    if (!blocks.containsKey(idx)) {
      _buffer.clear()
      val start = idx * blockSize
      channel.seek(start)
      if (channel.size - start < _buffer.limit) {
        _buffer.limit((channel.size - start).toInt)
      }
      val bytesToRead = _buffer.remaining()
      var attempts = 0
      val end = start + bytesToRead
      while (channel.position() < end && attempts < maxReadAttempts) {
        channel.read(_buffer)
        attempts += 1
      }

      if (channel.position() < end) {
        throw new IOException(
          s"Read ${channel.position() - start} of $bytesToRead bytes from $start in $attempts attempts"
        )
      }

      val dupe = ByteBuffer.allocate(bytesToRead)
      _buffer.position(0)
      dupe.put(_buffer)
      blocks.put(idx, dupe)
      dupe
    } else
      blocks.get(idx)

  def ensureBlocks(from: Long, to: Long): Unit =
    for {
      idx ← from to to
    } {
      getBlock(idx)
    }

  override def size: Long = channel.size

  override def _seek(newPos: Long): Unit = channel._seek(newPos)

  override protected def _read(dst: ByteBuffer): Unit = {
    val start = position()
    val end = start + dst.remaining()
    if (end > channel.size)
      throw new EOFException

    val startBlock = start / blockSize
    val endBlock = end / blockSize

    ensureBlocks(startBlock, endBlock)

    for {
      idx ← startBlock to endBlock
      blockStart = idx * blockSize
      blockEnd = (idx + 1) * blockSize
      from = max((start - blockStart).toInt, 0)
      to = (min(end, blockEnd) - blockStart).toInt
      blockBuffer = blocks.get(idx)
    } {
      blockBuffer.position(from)
      blockBuffer.limit(to)
      dst.put(blockBuffer)
      blockBuffer.clear()
    }
  }

  override def close(): Unit = {
    super.close()
    channel.close()
  }
}

object CachingChannel {

  /**
   * Configuration options for a [[CachingChannel]]
   *
   * @param blockSize size of blocks to cache
   * @param maxReadAttempts all read/skip operations require the full requested number of bytes to be returned in at most
   *                        this many attempts, or they will throw an [[IOException]].
   * @param maximumSize evict blocks from cache to avoid growing beyond this size
   */
  case class Config(blockSize: Int = 64 * 1024,
                    maxReadAttempts: Int = 2,
                    maximumSize: Long = 64 * 1024 * 1024)

  object Config {
    implicit val default = Config()
  }

  implicit def makeCachingChannel[Channel <: SeekableByteChannel](channel: Channel)(
      implicit config: Config
  ): CachingChannel[Channel] =
    CachingChannel(channel)

  implicit class AddCaching[Channel <: SeekableByteChannel](channel: Channel) {
    def cache(implicit config: Config): CachingChannel[Channel] =
      makeCachingChannel(channel)
  }
}

import java.nio.ByteBuffer

import org.abc.CachingChannel.makeCachingChannel

object MockChannel {
  val path = Paths.get("abc")
  val ch: SeekableByteChannel = SeekableByteChannel.apply(path)
  val buf = ByteBuffer.allocate(8)
  ch._read(buf)
}
