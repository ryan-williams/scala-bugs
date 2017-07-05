package org.abc

import java.io.InputStream

trait ByteChannel
  extends InputStream {

  override def read(): Int = 0

  protected def _read(s: String): Unit
}

object ByteChannel {
  implicit class InputStreamByteChannel(is: InputStream)
    extends ByteChannel {

    override protected def _read(s: String): Unit = ???
  }
}
