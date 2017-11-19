import org.scalatest.{ FunSuite, Matchers }

class FooTest
  extends FunSuite
    with Matchers
    with Foo {
  test("double") {
    Iterator(1, 2, 3).double.toList should be(Seq(2, 4, 6))
  }
}
