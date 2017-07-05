package org.abc

import java.io.{ EOFException, IOException, InputStream }
import java.nio.ByteBuffer

trait ByteChannel
  extends InputStream {

  protected var _position = 0L

  final def read(dst: ByteBuffer): Unit = ???

  override def read(): Int = 0

  override def read(b: Array[Byte], off: Int, len: Int): Int = 0

  protected def _read(dst: ByteBuffer): Unit

  def read(dst: ByteBuffer, offset: Int, length: Int): Unit = {
    dst.position(offset)
    val prevLimit = dst.limit()
    dst.limit(offset + length)
    read(dst)
    dst.limit(prevLimit)
  }

  def position(): Long = _position
}

object ByteChannel {

  implicit class InputStreamByteChannel(is: InputStream)
    extends ByteChannel {

    override def read(): Int = is.read()

    override protected def _read(dst: ByteBuffer): Unit = {
      val bytesToRead = dst.remaining()
      var bytesRead =
        is.read(
          dst.array(),
          dst.position(),
          dst.remaining()
        )

      if (bytesRead == -1)
        throw new EOFException

      val nextBytesRead =
        if (bytesRead < bytesToRead) {
          val moreBytesRead =
            is.read(
              dst.array(),
              dst.position() + bytesRead,
              dst.remaining() - bytesRead
            )

          if (moreBytesRead == -1)
            throw new EOFException

          bytesRead += moreBytesRead

          moreBytesRead
        } else
          0

      if (bytesRead < bytesToRead) {
        throw new IOException(
          s"Only read $bytesRead (${bytesRead - nextBytesRead} then $nextBytesRead) of $bytesToRead bytes from position ${position()}"
        )
      }

      dst.position(dst.position() + bytesRead)
    }
  }
}
