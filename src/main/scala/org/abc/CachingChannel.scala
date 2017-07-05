package org.abc

import java.nio.ByteBuffer

case class CachingChannel(channel: SeekableByteChannel)
  extends SeekableByteChannel {
  override protected def _read(dst: ByteBuffer): Unit = ???
}

object CachingChannel {

  case class Config(n: Int = 0)

  object Config {
    implicit val default = Config()
  }

  implicit def makeCachingChannel(channel: SeekableByteChannel)(
      implicit config: Config
  ): CachingChannel =
    CachingChannel(channel)
}
