package a

import shapeless._

trait TC1[T] { type Δ }

object TC1 {
  type Aux[T, D] = TC1[T] { type Δ = D }

  implicit def cmpCaseClassAll[T, L <: HList](
    implicit
    gen: Generic.Aux[T, L],
    cmp: TC1[L]
  ):
  TC1.Aux[T, cmp.Δ] = null

  implicit def cmpHNil[L <: HNil]: TC1.Aux[L, CNil] = null
}
