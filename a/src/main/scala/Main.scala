import scala.meta._

class Main extends scala.annotation.StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn match {
      case q"object $name { ..$stats }" =>
        q"""
        object $name {
          implicit def intToString(n: Int): String = n.toString
          def main(args: Array[String]): Unit = {
            ..$stats
          }
        }
          """
      case _ =>
        abort("@main must annotate an object.")
    }
  }
}
