```bash
sbt test
```

## JS output:
```
Suite:
- failure *** FAILED ***
  1 was not equal to 2 (Suite.scala:3)
  org.scalatest.exceptions.TestFailedException:
  at org.scalatest.exceptions.TestFailedException.captureStackTrace [as fillInStackTrace(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scala-js/scala-js/v0.6.27/library/src/main/scala/scala/scalajs/runtime/StackTrace.scala:55:51)
  at org.scalatest.exceptions.TestFailedException.fillInStackTrace(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scala-js/scala-js/v0.6.27/javalanglib/src/main/scala/java/lang/Throwables.scala:34:21)
  at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scala-js/scala-js/v0.6.27/javalanglib/src/main/scala/java/lang/Throwables.scala:21:49)
  at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/exceptions/StackDepthException.scala:42:11)
  at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/exceptions/TestFailedException.scala:54:11)
  at org.scalatest.exceptions.TestFailedException.call [as init(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/exceptions/TestFailedException.scala:82:7)
  at org.scalatest.Matchers$ShouldMethodHelper$.<init>(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/MatchersHelper.scala:234:11)
  at org.scalatest.Matchers$AnyShouldWrapper.shouldMatcher(/Users/ryan/c/scalac-bug/js/target/scala-2.12/https:/raw.githubusercontent.com/scalatest/scalatest/v3.0.5/scalatest.js/target/scala-2.12/src_managed/main/org/scalatest/Matchers.scala:6327:39)
  at Suite.should(/Users/ryan/c/scalac-bug/js/target/scala-2.12/file:/Users/ryan/c/scalac-bug/shared/src/test/scala/Suite.scala:3:31)
  at foo5__O__O__Lorg_scalatest_compatible_Assertion(/Users/ryan/c/scalac-bug/js/target/scala-2.12/file:/Users/ryan/c/scalac-bug/shared/src/test/scala/Suite.scala:4:33)
```

Only the last two lines give information about where the failure occurred, despite `-oF` ("full stack traces") being enabled.

This can be suboptimal when there are layers of assertion helpers / DSL on top of Scalatest's.

## JVM output:
In comparison, the JVM gives a full stack trace:

```
Suite:
- failure *** FAILED ***
  1 was not equal to 2 (Suite.scala:3)
  org.scalatest.exceptions.TestFailedException:
  at org.scalatest.MatchersHelper$.indicateFailure(MatchersHelper.scala:346)
  at org.scalatest.Matchers$ShouldMethodHelper$.shouldMatcher(Matchers.scala:6668)
  at org.scalatest.Matchers$AnyShouldWrapper.should(Matchers.scala:6704)
  at Suite.foo5(Suite.scala:3)
  at Suite.foo4(Suite.scala:4)
  at Suite.foo3(Suite.scala:5)
  at Suite.foo2(Suite.scala:6)
  at Suite.foo1(Suite.scala:7)
  at Suite.$anonfun$new$1(Suite.scala:9)
  at org.scalatest.OutcomeOf.outcomeOf(OutcomeOf.scala:85)
  at org.scalatest.OutcomeOf.outcomeOf$(OutcomeOf.scala:83)
  at org.scalatest.OutcomeOf$.outcomeOf(OutcomeOf.scala:104)
  at org.scalatest.Transformer.apply(Transformer.scala:22)
  at org.scalatest.Transformer.apply(Transformer.scala:20)
  at org.scalatest.FunSuiteLike$$anon$1.apply(FunSuiteLike.scala:186)
  at org.scalatest.TestSuite.withFixture(TestSuite.scala:196)
  at org.scalatest.TestSuite.withFixture$(TestSuite.scala:195)
  at org.scalatest.FunSuite.withFixture(FunSuite.scala:1560)
  at org.scalatest.FunSuiteLike.invokeWithFixture$1(FunSuiteLike.scala:184)
  at org.scalatest.FunSuiteLike.$anonfun$runTest$1(FunSuiteLike.scala:196)
…
```
