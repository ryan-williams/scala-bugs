package org.abc

import java.nio.file.Paths

import org.abc.CachingChannel.makeCachingChannel

object MockChannel {
  val path = Paths.get("abc")
  val ch = SeekableA(path)
  ch._read("abc")
}
