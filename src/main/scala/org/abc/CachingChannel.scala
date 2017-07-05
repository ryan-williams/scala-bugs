package org.abc

case class CachingChannel(channel: B)
  extends B {
  override protected def foo(s: String): Unit = ???
}

object CachingChannel {

  case class Config(n: Int = 0)

  object Config {
    implicit val default = Config()
  }

  implicit def makeCachingChannel(channel: B)(
      implicit config: Config
  ): CachingChannel =
    CachingChannel(channel)
}
