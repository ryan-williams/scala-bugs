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

trait B
  extends A

object B {

  class Config

  object Config {
    implicit val default = new Config
  }

  implicit def AtoB(a: A)(implicit config: Config): B = ???
}

object C {
  import org.abc.B.AtoB
  A().foo()
}
