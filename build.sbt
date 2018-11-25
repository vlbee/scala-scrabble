name := "scala-scrabble"

version := "0.1"

scalaVersion := "2.12.7"

lazy val myProject = (project in file("."))
  .settings(
    name := "My Project",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test,
  )