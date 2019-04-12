import sbt.TestFrameworks.ScalaTest
import sbtcrossproject.CrossPlugin.autoImport.crossProject
lazy val all =
  crossProject(JVMPlatform, JSPlatform)
    .in(file("."))
    .settings(
      scalaVersion := "2.12.8",
      libraryDependencies += "org.scalatest" %%% "scalatest" % "3.0.5" % "test",
      testOptions += Tests.Argument(ScalaTest, "-oF")
    )
