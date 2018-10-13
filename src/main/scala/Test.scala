import magnolia._
import org.hammerlab.test.Cmp

import scala.language.experimental.macros

trait TC[T]
object TC {
  type Typeclass[T] = TC[T]
  def  combine[T](ctx:   CaseClass[TC, T]): TC[T] = ???
  def dispatch[T](ctx: SealedTrait[TC, T]): TC[T] = ???
  implicit def gen[T]: TC[T] = macro Magnolia.gen[T]
}

case class A(value: Int)
case class B(ints: Array[A])

object Test {
  implicit def array[T](implicit cmp: Cmp[T]): TC[Array[T]] = ???
  TC.gen[B]  // 🚫: inst$macro$6  is already defined as value inst$macro$6
}
