## `provided` dependencies not on `run` classpath

```bash
sbt test     # spark-core is on classpath
sbt console  # spark-core is on classpath
scala> new org.apache.spark.SparkConf  # succeeds

sbt run      # fails: spark-core is not on classpath
```

The classpath for the `run` command should include `provided` dependencies.

[This StackOverflow is related](https://stackoverflow.com/questions/18838944/how-to-add-provided-dependencies-back-to-run-test-tasks-classpath/28200761#28200761).
