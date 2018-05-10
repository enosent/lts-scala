name := "lts-scala"

version := "0.1"

scalaVersion := "2.12.5"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.0",
  "org.json4s" %% "json4s-jackson" % "3.6.0-M3"
)
