package a

object A
  extends b.B {
  def main(args: Array[String]): Unit = {
    b()
    c()
  }
}
