import shapeless.{ the, Lazy }

// Stand-in for shapeless.Generic.Aux; theoretically easier to resolve because the Repr type-member is a type-param instead.
trait G[T, Repr]

// Likewise, HList and HNil replacements, to rule out any possible special treatment of those types in Lazy derivations
sealed trait HList
trait HNil extends HList
case object HNil extends HNil

// Example class with a simple "Generic" instance with Repr type HNil
trait A; object A { implicit val g: G[A, HNil] = null }

// Example output type to try to derive a TC instance for
trait Out

// Type-class mapping an input type to an output type. HNil ⇒ CNil instance is provided
trait TC[In] { type Out }
object TC {
  type Aux[In, Out_] = TC[In] { type Out = Out_ }
  implicit val hnil: TC.Aux[HNil, Out] = null
}

// If `T` is generic, and its generic repr has a `TC`, provide a `TC` for `T` with the same `Out` type
// Eager and Lazy versions are provided; the Lazy version does not work as expected
object derive {
  implicit def eager_[T, L <: HList](implicit g: G[T, L], tc:      TC[L]) : TC.Aux[T, tc      .Out] = null  // 👌🏻
  implicit def lazy_ [T, L <: HList](implicit g: G[T, L], tc: Lazy[TC[L]]): TC.Aux[T, tc.value.Out] = null  // 🐉
}

object testEager {
  import derive.eager_
  the[TC    [A     ]]  // ✅
  the[TC.Aux[A, Out]]  // ✅
}
object testLazy {
  import derive.lazy_
  the[TC    [A     ]]  // ✅
  the[TC.Aux[A, Out]]  // 🚫
}

object console {
import shapeless._
{ import derive.eager_; the[TC    [A      ]]                 }  // ✅
{ import derive.eager_; the[TC.Aux[A, Out]]                  }  // ✅
{ import derive.lazy_ ; the[TC    [A      ]]                 }  // ✅
{ import derive.lazy_ ; the[TC    [A      ]]: TC.Aux[A, Out] }  // ✅
{ import derive.lazy_ ; the[TC.Aux[A, Out]]             }  // 🚫
}
