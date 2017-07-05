package org.abc

import org.abc.C.makeCachingChannel

object D {
  val ch = B("zzz")
  ch.foo()
}
