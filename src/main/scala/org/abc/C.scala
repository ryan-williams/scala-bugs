package org.abc

case class C(n: Int) extends Ordered[C] {
  override def compare(that: C): Int = n - that.n
}

object C {

  implicit val ord = Ordering.by[(C, Boolean), C](_._1)

  /**
   * Fails to compile when this class is lexicographically greater than [[B]].
   */
  implicitly[Ordering[(C, Boolean)]]
}
