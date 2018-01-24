import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("HelloWorld")
object HelloWorld {
  println("yay")
  println(this)
  def hello(): Unit = {
    println("hello")
  }
}
