package org.abc

import java.nio.channels
import java.nio.file.Path

trait SeekableByteChannel
  extends ByteChannel

object SeekableByteChannel {
  implicit def apply(path: Path): SeekableByteChannel = ???
}

