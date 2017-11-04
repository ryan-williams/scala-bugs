lazy val macroAnnotationSettings = Seq(
  addCompilerPlugin("org.scalameta" % "paradise" % "3.0.0-M10" cross CrossVersion.full),
  scalacOptions += "-Xplugin-require:macroparadise",
  scalacOptions in (Compile, console) ~= (_ filterNot (_ contains "paradise")) // macroparadise plugin doesn't work in repl yet.
)

lazy val a = project.settings(
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided,
  macroAnnotationSettings
)

lazy val b = project.settings(
  libraryDependencies += "org.scalameta" %% "scalameta" % "1.8.0" % Provided,
  macroAnnotationSettings,
  resolvers += Resolver.mavenLocal
) dependsOn a
