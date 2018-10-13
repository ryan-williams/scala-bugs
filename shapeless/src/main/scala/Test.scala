object Test {
  import shapeless._
  import labelled.FieldType

  trait LL[T]  // typeclass with Lazy LabelledGeneric derivations
  implicit val hnilLL: LL[HNil] = ???
  implicit def consLL[K <: Symbol, H, T <: HList](implicit w: Witness.Aux[K], h: Lazy[LL[H]], t: Lazy[LL[T]]): LL[FieldType[K, H] :: T] = ???
  implicit def ccLL[CC, L <: HList](implicit g: LabelledGeneric.Aux[CC, L], l: Lazy[LL[L]]): LL[CC] = ???

  trait LG[T]  // typeclass with Lazy Generic derivations
  implicit val hnilLG: LG[HNil] = ???
  implicit def consLG[H, T <: HList](implicit h: Lazy[LG[H]], t: Lazy[LG[T]]): LG[H :: T] = ???
  implicit def ccLG[CC, L <: HList](implicit g: Generic.Aux[CC, L], l: Lazy[LG[L]]): LG[CC] = ???

  trait EL[T]  // typeclass with non-Lazy LabelledGeneric derivations
  implicit val hnilEL: EL[HNil] = ???
  implicit def consEL[K <: Symbol, H, T <: HList](implicit w: Witness.Aux[K], h: EL[H], t: EL[T]): EL[FieldType[K, H] :: T] = ???
  implicit def ccEL[CC, L <: HList](implicit g: LabelledGeneric.Aux[CC, L], l: EL[L]): EL[CC] = ???

  trait EG[T]  // typeclass with non-Lazy Generic derivations
  implicit val hnilEG: EG[HNil] = ???
  implicit def consEG[H, T <: HList](implicit h: EG[H], t: EG[T]): EG[H :: T] = ???
  implicit def ccEG[CC, L <: HList](implicit g: Generic.Aux[CC, L], l: EG[L]): EG[CC] = ???

  // tests on a type with a simple type-member:
   trait A { type T }
  object A { type Aux[_T] = A { type T = _T } }  // "Aux" alias
  case class A1[T](a1: A.Aux[T], a2: A.Aux[T])
  case class A2[T](a1: A1   [T])

  implicit def a1[T]: LL[A.Aux[T]] = ???
  implicit def a2[T]: LG[A.Aux[T]] = ???
  implicit def a3[T]: EL[A.Aux[T]] = ???
  implicit def a4[T]: EG[A.Aux[T]] = ???

  the[     LL[A.Aux[Int] ]]  // ✅
  the[     LG[A.Aux[Int] ]]  // ✅
  the[     EL[A.Aux[Int] ]]  // ✅
  the[     EG[A.Aux[Int] ]]  // ✅

  the[Lazy[LL[A.Aux[Int]]]]  // ✅
  the[Lazy[LG[A.Aux[Int]]]]  // ✅
  the[Lazy[EG[A.Aux[Int]]]]  // ✅
  the[Lazy[EL[A.Aux[Int]]]]  // ✅

  the[     LL[A1   [Int] ]]  // 🚫
  the[     LG[A1   [Int] ]]  // ✅
  the[     EL[A1   [Int] ]]  // ✅
  the[     EG[A1   [Int] ]]  // ✅

  the[     LL[A2   [Int] ]]  // 🚫
  the[     LG[A2   [Int] ]]  // ✅
  the[     EG[A2   [Int] ]]  // ✅ fixed in 2.13?
  the[     EL[A2   [Int] ]]  // ✅ fixed in 2.13?

  // tests on a type with a an HKT-member:
   trait B { type T[_] }
  object B { type Aux[_T[_]] = B { type T[U] = _T[U] } }  // "Aux" alias
  case class B1[T[_]](b1: B.Aux[T], b2: B.Aux[T])
  case class B2[T[_]](b1: B1   [T])

  implicit def b1[T[_]]: LL[B.Aux[T]] = ???
  implicit def b2[T[_]]: EL[B.Aux[T]] = ???
  implicit def b3[T[_]]: LG[B.Aux[T]] = ???
  implicit def b4[T[_]]: EG[B.Aux[T]] = ???

  the[     LL[B.Aux[List]] ]  // ✅
  the[     LG[B.Aux[List]] ]  // ✅
  the[     EL[B.Aux[List]] ]  // ✅
  the[     EG[B.Aux[List]] ]  // ✅

  the[Lazy[LL[B.Aux[List]]]]  // 🚫
  the[Lazy[LG[B.Aux[List]]]]  // 🚫
  the[Lazy[EL[B.Aux[List]]]]  // 🚫
  the[Lazy[EG[B.Aux[List]]]]  // 🚫

  the[     LL[B1   [List]] ]  // 🚫
  the[     LG[B1   [List]] ]  // 🚫
  the[     EL[B1   [List]] ]  // ✅
  the[     EG[B1   [List]] ]  // ✅

  the[     LL[B2   [List]] ]  // 🚫
  the[     LG[B2   [List]] ]  // 🚫
  the[     EL[B2   [List]] ]  // ✅ fixed in 2.13?
  the[     EG[B2   [List]] ]  // ✅ fixed in 2.13?
}
