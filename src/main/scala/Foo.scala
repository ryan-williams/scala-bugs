
import hammerlab.iterator.macros.IteratorOps

@IteratorOps
class Foo(it: Iterator[Int]) {
  def double: Iterator[Int] = it.map(_ * 2)
}
