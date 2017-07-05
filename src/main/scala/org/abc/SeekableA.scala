package org.abc

import java.nio.file.Path

trait SeekableA
  extends A

object SeekableA {
  def apply(path: Path): SeekableA = ???
}

