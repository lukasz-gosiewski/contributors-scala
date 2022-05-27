import sbt.Keys.libraryDependencies

name := "contributors-scala"

version := "0.1"
scalaVersion := "2.13.8"

val http4sVersion = "0.23.12"
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-circe",
  "org.http4s" %% "http4s-dsl",
  "org.http4s" %% "http4s-blaze-server",
  "org.http4s" %% "http4s-blaze-client"
).map(_ % http4sVersion)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.12" % "test"
libraryDependencies += "io.circe" %% "circe-generic-extras" % "0.14.1"
libraryDependencies += "org.typelevel" %% "cats-effect-testing-scalatest" % "1.4.0" % Test

