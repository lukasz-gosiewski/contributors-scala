import sbt.Keys.libraryDependencies

name := "contributors-scala"

version := "0.1"
scalaVersion := "2.13.1"

val http4sVersion = "0.21.3"
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

libraryDependencies += "com.typesafe.play" %% "play" % "2.8.2"
libraryDependencies += "com.softwaremill.sttp.client" %% "circe" % "2.0.0-RC7"
libraryDependencies += "com.softwaremill.sttp.client" %% "core" % "2.0.0-RC7"
libraryDependencies += "com.softwaremill.sttp.client" %% "httpclient-backend" % "2.0.0-RC7"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.0" % "test"
