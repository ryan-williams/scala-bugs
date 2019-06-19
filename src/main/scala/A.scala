import shapeless._

// Type that provides functionality in terms of a provided HList type
// - Conforms to `Generic` contract in terms of the wrapped HList
// - Generic HNil instance is provided
sealed trait A[+L <: HList]
object A {
  implicit val generic: Generic.Aux[A[HNil], HNil] = null
}

// Type-class mapping an input type to an output type. HNil ⇒ CNil instance is provided
trait TC[In] { type Out }
object TC {
  type Aux[In, Out_] = TC[In] { type Out = Out_ }
  implicit def hnil[L <: HNil]: TC.Aux[L, CNil] = null
}

// If `T` is generic, and its generic repr has a `TC`, provide a `TC` for `T` with the same `Out` type
// Eager and Lazy versions are provided; the Lazy version does not work as expected
object derive {
  implicit def eager_[T, L <: HList](implicit g: Generic.Aux[T, L], tc:      TC[L]) : TC.Aux[T, tc      .Out] = null  // 👌🏻
  implicit def lazy_ [T, L <: HList](implicit g: Generic.Aux[T, L], tc: Lazy[TC[L]]): TC.Aux[T, tc.value.Out] = null  // 🐉
}

object testEager {
  import derive.eager_
  the[TC    [A[HNil]      ]]  // ✅
  the[TC.Aux[A[HNil], CNil]]  // ✅
}
object testLazy {
  import derive.lazy_
  the[TC    [A[HNil]      ]]  // ✅
  the[TC.Aux[A[HNil], CNil]]  // 🚫
}

object console {
import shapeless._
{ import derive.eager_; the[TC    [A[HNil]      ]]                        }  // ✅
{ import derive.eager_; the[TC.Aux[A[HNil], CNil]]                        }  // ✅
{ import derive.lazy_ ; the[TC    [A[HNil]      ]]                        }  // ✅
{ import derive.lazy_ ; the[TC    [A[HNil]      ]]: TC.Aux[A[HNil], CNil] }  // ✅
{ import derive.lazy_; the[TC.Aux[A[HNil], CNil]]                    }  // 🚫
}
