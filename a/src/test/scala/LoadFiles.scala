import LoadFile.exists

object LoadFiles {
  def main(args: Array[String]): Unit = {
    exists("main.txt")
    exists("test.txt")
  }
}
