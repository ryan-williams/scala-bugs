package org.abc

case class C(n: Int) extends Ordered[C] {
  override def compare(that: C): Int = n - that.n
}

object C {
  /**
   * Fails to compile when this class is lexicographically greater than [[B]].
   */
  implicitly[Ordering[(C, Boolean)]]
}
