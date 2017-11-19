scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  "org.hammerlab" %% "iterator-macros" % "1.1.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
)

// scalameta configs for expanding IteratorOps macro
addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full)
scalacOptions += "-Xplugin-require:macroparadise"
libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % "provided"
