# scalac-bug
Minimal repro of a scalac bug I ran into.

See [A.scala](src/main/scala/org/abc/A.scala) or [this scastie](https://scastie.scala-lang.org/ryan-williams/gdJxMYE2Qr2vxkeeOY7ECg):

```scala
trait A {
  protected def foo(): Unit
}

object A {
  def apply(): A = ???
}

trait B extends A

object B {
  class Config
  implicit val default = new Config
  implicit def AtoB(a: A)(implicit config: Config): B = ???
}

object C {
  import B.AtoB
  A().foo()
}
```

## Repro steps:

### Clone repo:
```
git clone git@github.com:ryan-williams/scalac-bug.git
cd scalac-bug
```

### Compiling `A.scala` causes a stack overflow in the compiler:

```bash
$ scalac -version
Scala compiler version 2.12.2 -- Copyright 2002-2017, LAMP/EPFL and Lightbend, Inc.

$ scalac src/main/scala/org/abc/A.scala
error: java.lang.StackOverflowError
	at scala.reflect.internal.Trees$TreeContextApiImpl.modifyType(Trees.scala:198)
	at scala.reflect.internal.Trees$TypeMapTreeSubstituter.traverse(Trees.scala:1548)
	at scala.reflect.internal.Trees$TypeMapTreeSubstituter.traverse(Trees.scala:1546)
	at scala.reflect.internal.Trees$class.traverseComponents$1(Trees.scala:1294)
	at scala.reflect.internal.Trees$class.itraverse(Trees.scala:1330)
	at scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:16)
	at scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:16)
	at scala.reflect.api.Trees$Traverser.traverse(Trees.scala:2475)
	at scala.reflect.internal.Trees$TypeMapTreeSubstituter.traverse(Trees.scala:1552)
	at scala.reflect.internal.Trees$TypeMapTreeSubstituter.traverse(Trees.scala:1546)
	at scala.reflect.internal.Trees$class.traverseComponents$1(Trees.scala:1283)
	at scala.reflect.internal.Trees$class.itraverse(Trees.scala:1330)
	at scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:16)
	at scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:16)
	at scala.reflect.api.Trees$Traverser.traverse(Trees.scala:2475)
	at scala.reflect.internal.Trees$TypeMapTreeSubstituter.traverse(Trees.scala:1552)
	at scala.reflect.internal.Trees$TypeMapTreeSubstituter.traverse(Trees.scala:1546)
	at scala.reflect.internal.Trees$class.traverseComponents$1(Trees.scala:1283)
	…
```

### In repl
Pasting the contents of `A.scala` into a REPL also reproduces the issue.

## Discussion

A variety of tweaks to [A.scala](src/main/scala/org/abc/A.scala) avoid the compiler crash:

- comment out [line 21](src/main/scala/org/abc/A.scala#L21) (`A().foo()`)
- comment out [line 20](src/main/scala/org/abc/A.scala#L20) (`import B.AtoB`)
- remove the `(implicit config: Config)` from [line 16](src/main/scala/org/abc/A.scala#L16)
- remove the `protected` modifier from `A.foo` on [line 4](src/main/scala/org/abc/A.scala#L4)


The stack-overflow is primarily started by [line 21](src/main/scala/org/abc/A.scala#L21):

```scala
A().foo()
```

- the compiler seems to get tripped up trying to decide whether to call `A.foo` or `B.foo`.
- since `A.foo` is `protected` and inaccessible from `C`, an implicit search kicks off.
- the compiler seemingly considers `AtoB` and `B.foo` as an option, thanks to the import of `B.AtoB` on [line 20](src/main/scala/org/abc/A.scala#L20)..
- for some reason, `B.AtoB`'s requirement of an `implicit config: Config` is necessary to set off the infinite recursion.
