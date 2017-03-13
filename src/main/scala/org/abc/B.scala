package org.abc

trait B extends Comparable[B]

object B {
  /**
   * Iff this line is compiled before [[C]] below, then [[C]] will throw a "divergent implicit
   * expansion" error, though the compiler should not carry any state from here to there.
   */
  implicitly[Ordering[B]]
}
