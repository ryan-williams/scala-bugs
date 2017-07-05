# scalac-bug
Minimal repro of [SI-10222](https://issues.scala-lang.org/browse/SI-10222).

Three relevant `git` branches:

![](https://d3vv6lp55qjaqc.cloudfront.net/items/351r0A3B1R0H2e2e1P2X/Screen%20Shot%202017-07-05%20at%201.11.05%20PM.png)

## Steps:

##### Clone repo:
```
git clone git@github.com:ryan-williams/scalac-bug.git
cd scalac-bug
```

##### Compiling `B.scala` and `C.scala`: **fails**:

```bash
$ scalac src/main/scala/org/abc/{B,C}.scala
src/main/scala/org/abc/C.scala:11: error: diverging implicit expansion for type Ordering[(org.abc.C, Boolean)]
starting with method $conforms in object Predef
  implicitly[Ordering[(C, Boolean)]]
            ^
one error found
```

##### Compiling `C.scala` and `B.scala`: succeeds

```bash
$ scalac src/main/scala/org/abc/{C,B}.scala
$ rm -rf org  # clean up generated .class files
```

##### `sbt compile` a working branch causes broken branch to require `sbt clean`

```bash
$ git checkout spoil
$ sbt clean compile    # succeeds, correctly
$ git checkout 10222
$ sbt compile          # succeeds, (erroneously?)
$ sbt clean compile    # fails, (correctly?)
```
