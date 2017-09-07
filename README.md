# SBT causes Java serialization issue

Tests pass via Maven:

```bash
mvn test  # Succeeds
```

but fail via SBT:

```bash
sbt test  $ Fails!
# java.lang.ClassCastException: cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.B.as of type scala.collection.immutable.List in instance of org.B
# 	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2133)
# 	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1305)
# 	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2024)
# 	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
# 	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
# 	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
# 	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
# 	at org.Serde$.deserialize(Serde.scala:20)
# 	at org.Serde$.main(Serde.scala:28)
# 	at org.SerdeTest.<init>(SerdeTest.scala:7)
```

Things run fine via a `sbt package` JAR:

```bash
sbt package
scala target/scala-2.11/scalac-bug_2.11-0.1-SNAPSHOT.jar
# B(List(A(abc), A(def)))
```

but fail when run by SBT:

```bash
sbt run  # Fails!
# java.lang.ClassCastException: cannot assign instance of scala.collection.immutable.List$SerializationProxy to field org.B.as of type scala.collection.immutable.List in instance of org.B
# 	at java.io.ObjectStreamClass$FieldReflector.setObjFieldValues(ObjectStreamClass.java:2133)
# 	at java.io.ObjectStreamClass.setObjFieldValues(ObjectStreamClass.java:1305)
# 	at java.io.ObjectInputStream.defaultReadFields(ObjectInputStream.java:2024)
# 	at java.io.ObjectInputStream.readSerialData(ObjectInputStream.java:1942)
# 	at java.io.ObjectInputStream.readOrdinaryObject(ObjectInputStream.java:1808)
# 	at java.io.ObjectInputStream.readObject0(ObjectInputStream.java:1353)
# 	at java.io.ObjectInputStream.readObject(ObjectInputStream.java:373)
# 	at org.Serde$.deserialize(Serde.scala:20)
# 	at org.Serde$.main(Serde.scala:28)
# 	at org.Serde.main(Serde.scala)
# 	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
# 	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
# 	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
# 	at java.lang.reflect.Method.invoke(Method.java:498)
```

SBT succeeds if the `List` member in `B` (and corresponding instantiation argument in `Serde.main`) is changed to an `Array`, `Vector`, or `ArrayBuffer`; the `List.SerializationProxy` is triggering the issue somehow.

The issue shows up in SBT {0.13.13, 0.13.16, 1.0.1} x Scala {2.11.11, 2.12.3}.
