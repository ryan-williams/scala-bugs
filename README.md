```bash
sbt test
```

## JS output:
```
[info] Suite:
[info] - failure *** FAILED ***
[info]   1 was not equal to 2 (Suite.scala:3)
[info]   org.scalatest.exceptions.TestFailedException:
[info]   at org.scalatest.exceptions.TestFailedException.captureStackTrace [as fillInStackTrace(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scala-js/scala-js/v0.6.27/library/src/main/scala/scala/scalajs/runtime/StackTrace.scala:55:51)
[info]   at org.scalatest.exceptions.TestFailedException.fillInStackTrace(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scala-js/scala-js/v0.6.27/javalanglib/src/main/scala/java/lang/Throwables.scala:34:21)
[info]   at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scala-js/scala-js/v0.6.27/javalanglib/src/main/scala/java/lang/Throwables.scala:21:49)
[info]   at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/exceptions/StackDepthException.scala:42:11)
[info]   at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/exceptions/TestFailedException.scala:54:11)
[info]   at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/exceptions/TestFailedException.scala:82:7)
[info]   at org.scalatest.Matchers$ShouldMethodHelper$.<init>(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/MatchersHelper.scala:234:11)
[info]   at org.scalatest.Matchers$AnyShouldWrapper.shouldMatcher(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/Matchers.scala:6327:39)
[info]   at Suite.should(/Users/ryan/c/scalac-bug/js/target/scala-2.12/file:/Users/ryan/c/scalac-bug/shared/src/test/scala/Suite.scala:3:31)
[info]   at foo5__O__O__Lorg_scalatest_compatible_Assertion(/Users/ryan/c/scalac-bug/js/target/scala-2.12/file:/Users/ryan/c/scalac-bug/shared/src/test/scala/Suite.scala:4:33)
```

Only the last two lines give information about where the failure occurred, despite `-oF` ("full stack traces") being enabled.

This can be suboptimal when there are layers of assertion helpers / DSL on top of Scalatest's.

## JVM output:
In comparison, the JVM gives a full stack trace:

```
[info] Suite:
[info] - failure *** FAILED ***
[info]   1 was not equal to 2 (Suite.scala:3)
[info]   org.scalatest.exceptions.TestFailedException:
[info]   at org.scalatest.MatchersHelper$.indicateFailure(MatchersHelper.scala:346)
[info]   at org.scalatest.Matchers$ShouldMethodHelper$.shouldMatcher(Matchers.scala:6668)
[info]   at org.scalatest.Matchers$AnyShouldWrapper.should(Matchers.scala:6704)
[info]   at Suite.foo5(Suite.scala:3)
[info]   at Suite.foo4(Suite.scala:4)
[info]   at Suite.foo3(Suite.scala:5)
[info]   at Suite.foo2(Suite.scala:6)
[info]   at Suite.foo1(Suite.scala:7)
[info]   at Suite.$anonfun$new$1(Suite.scala:9)
[info]   at org.scalatest.OutcomeOf.outcomeOf(OutcomeOf.scala:85)
[info]   at org.scalatest.OutcomeOf.outcomeOf$(OutcomeOf.scala:83)
[info]   at org.scalatest.OutcomeOf$.outcomeOf(OutcomeOf.scala:104)
[info]   at org.scalatest.Transformer.apply(Transformer.scala:22)
[info]   at org.scalatest.Transformer.apply(Transformer.scala:20)
[info]   at org.scalatest.FunSuiteLike$$anon$1.apply(FunSuiteLike.scala:186)
[info]   at org.scalatest.TestSuite.withFixture(TestSuite.scala:196)
[info]   at org.scalatest.TestSuite.withFixture$(TestSuite.scala:195)
[info]   at org.scalatest.FunSuite.withFixture(FunSuite.scala:1560)
[info]   at org.scalatest.FunSuiteLike.invokeWithFixture$1(FunSuiteLike.scala:184)
[info]   at org.scalatest.FunSuiteLike.$anonfun$runTest$1(FunSuiteLike.scala:196)
…
```
