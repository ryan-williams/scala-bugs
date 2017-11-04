package c

@a.AddImplicit
trait C {}

object C extends C {
  val s: String = 4
}
