package b

import shapeless._

trait TC2[T] { type Δ }

object TC2 {
  type Aux[T, D] = TC2[T] { type Δ = D }

  implicit def cmpCaseClassAll[T, L <: HList](
    implicit
    gen: Generic.Aux[T, L],
    cmp: Lazy[TC2[L]]
  ):
    TC2.Aux[T, cmp.value.Δ] = null

  implicit def cmpHNil[L <: HNil]: TC2.Aux[L, CNil] = null
}
