package org.abc

trait I

trait A
  extends I {
  protected def foo(): Unit
}

object A {

  def apply(): A = ???

  implicit class ItoA(i: I)
    extends A {
    override protected def foo(): Unit = ???
  }
}
