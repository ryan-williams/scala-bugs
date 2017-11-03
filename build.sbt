
scalaVersion := "2.12.3"

val defaults = Seq(
)

lazy val a = project.settings(
  defaults
).dependsOn(
  b % "compile->compile;test->test",
  c % "test->test"
)

lazy val b = project.settings(
  defaults,
  publishArtifact in Test := true
)

lazy val c = project.settings(
  defaults,
  publishArtifact in Test := true
)
