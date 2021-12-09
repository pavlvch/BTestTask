name := "BankifiTestTask"

version := "0.1"

scalaVersion := "2.13.7"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.6.16",
  "com.typesafe.akka" %% "akka-stream" % "2.6.16",

  "com.typesafe.akka" %% "akka-http" % "10.2.6",
  "com.typesafe.akka" %% "akka-http-core" % "10.2.6",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.2.6",
  "io.circe" %% "circe-parser" % "0.14.1",
  "log4j" % "log4j" % "1.2.14",
  "org.typelevel" %% "cats-core" % "2.3.0"
)

