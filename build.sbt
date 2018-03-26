name := """weather-service-play"""
organization := "com.weather"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice
  ,"org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
  // Latest release for Kafka 1.0.0:
  ,"net.cakesolutions" %% "scala-kafka-client" % "1.0.0"
  ,"com.typesafe.akka" %% "akka-stream-kafka" % "0.19"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.weather.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.weather.binders._"

resolvers += Resolver.bintrayRepo("cakesolutions", "maven")

routesGenerator := InjectedRoutesGenerator