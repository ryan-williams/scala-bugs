## test-scoped project-dependency

Module `a` has `test->test` dependencies on `b` and `c`, but `a/publishM2` results in a POM without relevant `<dependency>` tags that include:
 
 ```xml
 <scope>test</scope>
 <classifier>tests</classifier>` entries
 ````
 
 ### Repro:
 
 ```bash
 $ sbt ++2.12.4 a/makePom
 $ cat a/target/scala-2.12/a_2.12-0.1-SNAPSHOT.pom
 …
    <dependencies>
		 <dependency>
             <groupId>org.scala-lang</groupId>
             <artifactId>scala-library</artifactId>
             <version>2.12.4</version>
         </dependency>
         <dependency>
             <groupId>b</groupId>
             <artifactId>b_2.12</artifactId>
             <version>0.1-SNAPSHOT</version>
         </dependency>
         <dependency>
             <groupId>c</groupId>
             <artifactId>c_2.12</artifactId>
             <version>0.1-SNAPSHOT</version>
             <scope>test</scope>
         </dependency>
    </dependencies>
```

### Work-around

Adding this block to your SBT config hackily works around this issue:

```scala
projectDependencies := projectDependencies.value.flatMap {
dep ⇒
  val configurations = dep.configurations.toSeq.flatMap(_.split(";"))
  val (testConfs, otherConfs) = configurations.partition(_ == "test->test")
  val testConf = testConfs.headOption
  if (testConf.isDefined)
    Seq(
      dep.copy(
        configurations =
          if (otherConfs.nonEmpty)
            Some(otherConfs.mkString(";"))
          else
            None
      ),
      dep
        .copy(
          configurations = Some("test->test")
        )
        .classifier("tests")
    )
  else
	Seq(dep)
}
```

(as seen in [`org.hammerlab:sbt-parent:3.4.0`](https://github.com/hammerlab/sbt-parent/blob/3.4.0/src/main/scala/org/hammerlab/sbt/plugin/Deps.scala)).
