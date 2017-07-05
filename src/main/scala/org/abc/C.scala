package org.abc

case class C(b: B)
  extends B {
  override protected def foo(): Unit = ???
}

object C {

  case class Config(n: Int = 0)

  object Config {
    implicit val default = Config()
  }

  implicit def makeCachingChannel(b: B)(
      implicit config: Config
  ): C =
    C(b)
}
