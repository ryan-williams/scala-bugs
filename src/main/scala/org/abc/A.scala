package org.abc

import java.io.InputStream

trait A
  extends InputStream {

  override def read(): Int = 0

  protected def foo(s: String): Unit
}

object A {
  implicit class InputStreamA(is: InputStream)
    extends A {

    override protected def foo(s: String): Unit = ???
  }
}
