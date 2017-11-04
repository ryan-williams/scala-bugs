
@a.AddImplicit
trait C1 {}

object C2 extends C1 {
  val foo = intToString _
  val s: String = 4
}

object C3 extends C1
