package org.abc

import java.nio.file.Paths

import org.abc.C.makeCachingChannel

object MockChannel {
  val path = Paths.get("abc")
  val ch = B(path)
  ch.foo("abc")
}
