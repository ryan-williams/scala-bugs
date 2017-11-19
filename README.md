## Scalameta-macro / scoverage source root issue

```bash
sbt clean coverage test coverageReport
…
[info] All tests passed.
[success] Total time: 12 s, completed Nov 19, 2017 8:20:02 PM
[info] Waiting for measurement data to sync...
[info] Reading scoverage instrumentation [/Users/ryan/c/scalac-bug/target/scala-2.11/scoverage-data/scoverage.coverage.xml]
[info] Reading scoverage measurements...
[info] Generating scoverage reports...
java.lang.RuntimeException: No source root found for '/Users/ryan/c/scalac-bug/<macro>' (source roots: '/Users/ryan/c/scalac-bug/src/main/scala/')
	at scoverage.report.BaseReportWriter.relativeSource(BaseReportWriter.scala:28)
	at scoverage.report.BaseReportWriter.relativeSource(BaseReportWriter.scala:16)
	at scoverage.report.CoberturaXmlWriter.klass(CoberturaXmlWriter.scala:42)
	at scoverage.report.CoberturaXmlWriter$$anonfun$pack$1.apply(CoberturaXmlWriter.scala:66)
	at scoverage.report.CoberturaXmlWriter$$anonfun$pack$1.apply(CoberturaXmlWriter.scala:66)
	at scala.collection.TraversableLike$$anonfun$map$1.apply(TraversableLike.scala:244)
	at scala.collection.TraversableLike$$anonfun$map$1.apply(TraversableLike.scala:244)
	at scala.collection.immutable.List.foreach(List.scala:318)
```
