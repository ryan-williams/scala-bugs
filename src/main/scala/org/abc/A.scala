package org.abc

import java.io.InputStream

trait I

trait A
  extends I {
  protected def foo(s: String): Unit
}

object A {
  implicit class InputStreamA(i: I)
    extends A {
    override protected def foo(s: String): Unit = ???
  }
}
