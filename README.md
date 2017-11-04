## Annotation Macros Unsupported in IntelliJ

#### Five small modules:

- `a`: defines an annotation macro
	- `b`: `dependsOn` `a`, invokes macro
		- `d`: `dependsOn` `b`, uses macro-generated code
	- `c`: "library dependency" on `a`, invokes macro
		- `e`: "library dependency" on `c`, uses macro-generated code

#### The project compiles fine:

```bash
sbt clean a/publishM2 c/publishM2 test:compile
```

#### …but IntelliJ red-lines all uses of macro-generated code:

- [`B.scala`](b/src/main/scala/B.scala):

	![](https://cl.ly/22053K041t1C/Screen%20Shot%202017-11-04%20at%2011.18.11%20AM.png)

- [`B4.scala`](b/src/test/scala/B4.scala) (`b` test file):

	![](https://cl.ly/2u450q0X1h40/Screen%20Shot%202017-11-04%20at%2011.20.28%20AM.png)

- [`C.scala`](c/src/main/scala/C.scala):

	![](https://cl.ly/2f0P2L3C0q1G/Screen%20Shot%202017-11-04%20at%2011.19.17%20AM.png)

- [`D.scala`](d/src/main/scala/D.scala):

	![](https://cl.ly/0A002K3c3036/Screen%20Shot%202017-11-04%20at%2011.22.55%20AM.png)

- [`E.scala`](e/src/main/scala/E.scala):

	![](https://cl.ly/0L2Q2b0W250z/Screen%20Shot%202017-11-04%20at%2011.23.34%20AM.png)

