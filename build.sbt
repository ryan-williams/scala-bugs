lazy val a = project.dependsOn(
  b % "compile->compile;test->test",
  c % "test->test"
)
lazy val b = project.settings(publishArtifact in Test := true)
lazy val c = project.settings(publishArtifact in Test := true)
