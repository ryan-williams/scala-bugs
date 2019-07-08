import shapeless.{ Lazy, the }

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
    //the[TC.Aux[CC, O]]                   // 🚫: Lazy breaks the derivation's awareness of the "output" type, for some reason

    // Further checks:
    the[TC    [CC     ]]: TC.Aux[CC, O]  // ✅
    the[TC    [CC     ]](lazy_)          // ✅
    //the[TC.Aux[CC, O]](lazy_)            // 🚫: "error: polymorphic expression cannot be instantiated to expected type; "
    the[TC.Aux[CC, O]](lazy_[CC])        // ✅
  }
}

object console {
  import shapeless._
  { import derive.eager_; the[TC    [CC   ]]                }  // ✅: res0: TC.Aux[CC,TC.base.Out] = null
  { import derive.eager_; the[TC.Aux[CC, O]]                }  // ✅: res1: TC.Aux[CC,TC.base.Out] = null
  { import derive.lazy_ ; the[TC    [CC   ]]                }  // ✅: res2: TC[CC]{type Out = O} = null
  { import derive.lazy_ ; the[TC    [CC   ]]: TC.Aux[CC, O] }  // ✅: res3: TC.Aux[CC,O] = null
  { import derive.lazy_ ; the[TC.Aux[CC, O]]                }  // 🚫
  // <console>:15: error: could not find implicit value for parameter t: TC.Aux[CC,O]
  //   { import derive.lazy_ ; the[TC.Aux[CC, O]]           }  // 🚫
  //                              ^
}
