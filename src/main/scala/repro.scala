import shapeless.{ the, Lazy }

trait CC  // stand-in for a case-class that we with to derive a typeclass instance `TC` for
trait L   // stand-in for a generic HList Repr of the case-class `CC`
trait O   // output-type for `L` (and ideally `CC`) from the typeclass `TC` below

// Imitation of shapeless.Generic.Aux
// - sole instance binds L as CC's "Repr"
// - theoretically easier to resolve as the "Repr" type-member is instead a type-param
trait G[T, Repr]; object G { implicit val g: G[CC, L] = null }

// Typeclass with one instance mapping `L` to `Out`
trait TC[In] { type Out }
object TC {
  type Aux[In, Out_] = TC[In] { type Out = Out_ }
  implicit val base: TC.Aux[L, O] = null
}

// A derivation of typeclass `TC` for the case-class `CC` in terms of its Repr `L` and an existing TC[L], which reuses the same `Out` type
// THe "eager" version works as expected, but the "lazy" version does not.
object derive {
  implicit def eager_[T](implicit g: G[T, L], tc:      TC[L]) : TC.Aux[T, tc      .Out] = null  // 👌🏻
  implicit def lazy_ [T](implicit g: G[T, L], tc: Lazy[TC[L]]): TC.Aux[T, tc.value.Out] = null  // 🐉
}

object test {
  {
    import derive.eager_
    the[TC    [CC   ]]  // ✅
    the[TC.Aux[CC, O]]  // ✅
  }

  {
    import derive.lazy_
    the[TC    [CC   ]]                   // ✅
    the[TC.Aux[CC, O]]                   // 🚫

    // Further checks:
    the[TC    [CC     ]]: TC.Aux[CC, O]  // ✅
    the[TC    [CC     ]](lazy_)          // ✅
    the[TC.Aux[CC, O]](lazy_)            // 🚫
    the[TC.Aux[CC, O]](lazy_[CC])        // ✅
  }
}
