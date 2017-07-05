package org.abc

class B
  extends A {
  override protected def foo(): Unit = ???
}

object B {

  case class Config(n: Int)

  object Config {
    implicit val default = Config(0)
  }

  implicit def makeCachingChannel(a: A)(implicit config: Config): B = ???
}
