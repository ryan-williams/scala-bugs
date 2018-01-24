
name := "hello"
scalaVersion := "2.12.4"

enablePlugins(
  ScalaJSPlugin,
  ScalaJSBundlerPlugin
)

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"

