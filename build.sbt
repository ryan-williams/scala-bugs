lazy val shapeless = project.settings(
  scalaVersion := "2.12.7",
  libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.3"
)

lazy val magnolia = project.settings(
  scalaVersion := "2.12.7",
  libraryDependencies += "com.propensive" %% "magnolia" % "0.10.0"
)

lazy val m2 = project.settings(
  scalaVersion := "2.11.12",
  libraryDependencies ++=
    Seq(
      "com.propensive" %% "magnolia" % "0.10.0",
      "org.hammerlab.test" %% "suite" % "1.0.3"
    )
)
