package org.abc

trait A {
  protected def foo(): Unit
}

object A {
  def apply(): A = ???
}

trait B extends A

object B {
  class Config
  implicit val default = new Config
  implicit def AtoB(a: A)(implicit config: Config): B = ???
}

object C {
  import B.AtoB
  A().foo()
}
