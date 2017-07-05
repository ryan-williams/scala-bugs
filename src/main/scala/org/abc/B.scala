package org.abc

trait B
  extends A

object B {

  class Config

  object Config {
    implicit val default = new Config
  }

  implicit def AtoB(a: A)(implicit config: Config): B = ???
}
