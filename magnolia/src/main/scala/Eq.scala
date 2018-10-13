import magnolia._
import scala.language.experimental.macros

/** typeclass for testing the equality of two values of the same type */
trait Eq[T] { def equal(value: T, value2: T): Boolean }

/** companion object to [[Eq]] */
object Eq {

  /** type constructor for the equality typeclass */
  type Typeclass[T] = Eq[T]

  /** defines equality for this case class in terms of equality for all its parameters */
  def combine[T](ctx: CaseClass[Eq, T]): Eq[T] = new Eq[T] {
    def equal(value1: T, value2: T) = ctx.parameters.forall { param =>
      param.typeclass.equal(param.dereference(value1), param.dereference(value2))
    }
  }

  /** choose which equality subtype to defer to
   *
   *  Note that in addition to dispatching based on the type of the first parameter to the `equal`
   *  method, we check that the second parameter is the same type. */
  def dispatch[T](ctx: SealedTrait[Eq, T]): Eq[T] = new Eq[T] {
    def equal(value1: T, value2: T): Boolean = ctx.dispatch(value1) {
      sub ⇒
        sub.cast.isDefinedAt(value2) &&
        sub.typeclass.equal(sub.cast(value1), sub.cast(value2))
    }
  }

  /** equality typeclass instance for strings */
  implicit val string: Eq[String] = new Eq[String] { def equal(v1: String, v2: String) = v1 == v2 }

  /** equality typeclass instance for integers */
  implicit val int: Eq[Int] = new Eq[Int] { def equal(v1: Int, v2: Int) = v1 == v2 }

  /** binds the Magnolia macro to the `gen` method */
  implicit def gen[T]: Eq[T] = macro Magnolia.gen[T]
}

object Test {
  // tests on a type with a simple type-member:
  trait A { type T }
  object A { type Aux[_T] = A { type T = _T } }  // "Aux" alias
  case class A1[T](a1: A.Aux[T], a2: A.Aux[T])
  case class A2[T](a1: A1   [T])

  implicit def a[T]: Eq[A.Aux[T]] = ???

  // tests on a type with a an HKT-member:
  trait B { type T[_] }
  object B { type Aux[_T[_]] = B { type T[U] = _T[U] } }  // "Aux" alias
  case class B1[T[_]](b1: B.Aux[T], b2: B.Aux[T])
  case class B2[T[_]](b1: B1   [T])

  implicit def b[T[_]]: Eq[B.Aux[T]] = ???

  implicitly[Eq[A.Aux[Int]]]
  implicitly[Eq[A1[Int]]]
  implicitly[Eq[A2[Int]]]

  implicitly[Eq[B.Aux[List]]]
  implicitly[Eq[B1[List]]]
  implicitly[Eq[B2[List]]]
}
