import java.lang.Thread.currentThread

object LoadFile {
  def exists(path: String): Unit = {
    val exists = currentThread().getContextClassLoader.getResource(path) != null
    println(s"$path: $exists")
  }

  def main(args: Array[String]): Unit = exists("main.txt")
}
