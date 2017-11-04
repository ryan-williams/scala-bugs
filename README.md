## scalameta macro annotation example

- [build.sbt](build.sbt) and [a/src/main/scala/Main.scala](a/src/main/scala/Main.scala) contain the example code from [http://scalameta.org/paradise/#HelloWorld](http://scalameta.org/paradise/#HelloWorld)
- [b/src/main/scala/B.scala](b/src/main/scala/B.scala) attempts to use the macro defined in [a/src/main/scala/Main.scala](a/src/main/scala/Main.scala) 

```bash
sbt a/publishM2
sbt b/compile
…
[error] /…/b/src/main/scala/B.scala:2:8: macro annotation could not be expanded (the most common reason for that is that you need to enable the macro paradise plugin; another possibility is that you try to use macro annotation in the same compilation run that defines it)
[error] object B {
[error]        ^
```
