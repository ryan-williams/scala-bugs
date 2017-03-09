# scalac-bug
Minimal repro of a scalac bug I ran into.

Three relevant `git` branches:

![](https://d3vv6lp55qjaqc.cloudfront.net/items/1P0d2b120K203K1i2Y20/Screen%20Shot%202017-03-08%20at%209.59.13%20PM.png)

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
$ git checkout master
$ sbt compile          # succeeds, (erroneously?)
$ sbt clean compile    # fails, (correctly?)
```
