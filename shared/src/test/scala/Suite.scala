import org.scalatest._
class Suite extends FunSuite with Matchers {
  def foo5[T](l: T, r: T) = l should be(r)
  def foo4[T](l: T, r: T) = foo5(l, r)
  def foo3[T](l: T, r: T) = foo4(l, r)
  def foo2[T](l: T, r: T) = foo3(l, r)
  def foo1[T](l: T, r: T) = foo2(l, r)
  test("failure") {
    foo1(1, 2)
  }
}
