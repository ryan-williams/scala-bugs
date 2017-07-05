package org.abc

import java.nio.ByteBuffer
import java.nio.file.Paths

import org.abc.CachingChannel.makeCachingChannel

object MockChannel {
  val path = Paths.get("abc")
  val ch: SeekableByteChannel = SeekableByteChannel.apply(path)
  val buf = ByteBuffer.allocate(8)
  ch._read(buf)
}
