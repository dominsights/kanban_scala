val scala3Version = "3.0.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "kanban_scala",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies += "com.novocode" % "junit-interface" % "0.11" % "test",
libraryDependencies += "org.scalameta" %% "munit" % "0.7.26" % Test,
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.15.4",
    testFrameworks += new TestFramework("munit.Framework")

  )
