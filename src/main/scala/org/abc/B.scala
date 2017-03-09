package org.abc

trait B extends Comparable[B]

object B {
  implicitly[Ordering[B]]
}
