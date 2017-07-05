package org.abc

import java.io.IOException
import java.nio.file.Files.newByteChannel
import java.nio.file.Path
import java.nio.{ ByteBuffer, channels }

trait SeekableByteChannel
  extends ByteChannel

object SeekableByteChannel {
  case class ChannelByteChannel(ch: channels.SeekableByteChannel)
    extends SeekableByteChannel {

    override def _read(dst: ByteBuffer): Unit = {
      val n = dst.remaining()
      var read = ch.read(dst)

      if (read < n)
        read += ch.read(dst)

      if (read < n)
        throw new IOException(
          s"Only read $read of $n bytes in 2 tries from position ${position()}"
        )
    }
    override def position(): Long = ch.position()
  }

  implicit def makeChannelByteChannel(ch: channels.SeekableByteChannel): ChannelByteChannel =
    ChannelByteChannel(ch)

  implicit def apply(path: Path): ChannelByteChannel =
    ChannelByteChannel(
      newByteChannel(
        path
      )
    )
}

