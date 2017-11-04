
val scala212 = scalaVersion := "2.12.4"
val macroParadise = addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
val scalaReflect = libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
val skipDoc = publishArtifact in (Compile, packageDoc) := false

// Defines a macro
lazy val a = project.settings(scala212, scalaReflect, macroParadise)

// dependsOn `a`
lazy val b = project.settings(scala212, macroParadise) dependsOn a

// LibraryDependency on `a`
lazy val c = project.settings(
  scala212,
  macroParadise,
  skipDoc,  // work-around unrelated bug, probably https://github.com/scala/bug/issues/8771
  libraryDependencies += "a" %% "a" % "0.1-SNAPSHOT"
)

// dependsOn `b`
lazy val d = project.settings(scala212) dependsOn b

// libraryDependency on `c`
lazy val e = project.settings(
  scala212,
  libraryDependencies += "c" %% "c" % "0.1-SNAPSHOT"
)
