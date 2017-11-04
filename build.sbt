
val scala212 = scalaVersion := "2.12.4"
val usesMacro = addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
val scalaReflect = libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value

// Defines a macro
lazy val a = project.settings(scala212, scalaReflect)

// dependsOn `a`
lazy val b = project.settings(scala212, usesMacro) dependsOn a

// LibraryDependency on `a`
lazy val c = project.settings(
  scala212,
  usesMacro,
  libraryDependencies += "a" %% "a" % "0.1-SNAPSHOT"
)
