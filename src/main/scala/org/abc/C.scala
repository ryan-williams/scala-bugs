package org.abc

class C
  extends A {
  override protected def foo(): Unit = ???
}

object C {

  case class Config(n: Int)

  object Config {
    implicit val default = Config(0)
  }

  implicit def makeCachingChannel(a: A)(implicit config: Config): C = ???
}
