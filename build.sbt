
crossScalaVersions := Seq("2.11.11", "2.12.3")

val defaults = Seq(
  organization := "com.foo",
  version := "1.0-SNAPSHOT"
)

lazy val a = project.settings(
  defaults,
  libraryDependencies ++= Seq(
    "com.foo" %% "b" % "1.0-SNAPSHOT",
    "com.foo" %% "c" % "1.0-SNAPSHOT"
  )
)

lazy val b = project.settings(
  defaults,
  libraryDependencies += "com.foo" %% "c" % "1.0-SNAPSHOT"
)

lazy val c = project.settings(
  defaults
)
