package org

import java.io._

case class A(s: String)
case class B(as: List[A])

object Serde {
  def serialize(o: Object): Array[Byte] = {
    val baos = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(baos)
    oos.writeObject(o)
    oos.close()
    baos.toByteArray
  }

  def deserialize[T](bytes: Array[Byte]): T = {
    val ois = new ObjectInputStream(new ByteArrayInputStream(bytes))
    try {
      ois.readObject().asInstanceOf[T]
    } finally {
      ois.close()
    }
  }

  def main(args: Array[String]): Unit = {
    val b = B(List(A("abc"), A("def")))
    val b2 = deserialize[B](serialize(b))
    println(b2)
  }
}
