
import scala.annotation.{ StaticAnnotation, compileTimeOnly }
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

@compileTimeOnly("enable macro paradise to expand macro annotations")
class AddImplicit extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro AddImplicit.impl
}

object AddImplicit {
  def impl(c: whitebox.Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList

    val (expr, rest) =
      inputs match {
        case q"$_ trait $name { ..$body }" :: rest ⇒
          val expr =
            c.Expr[Any](
              q"""
                trait $name {
                  implicit def intToString(n: Int): String = n.toString

                 ..$body
                }
              """
            )

          (expr, rest)
        case _ ⇒
          throw new Exception(s"expected ClassDef: $inputs")

      }

    c.Expr[Any](
      Block(
        expr.tree :: rest,
        Literal(Constant(()))
      )
    )
  }
}
