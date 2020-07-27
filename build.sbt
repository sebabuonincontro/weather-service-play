name := """weather-service-play"""
organization := "com.weather"

version := "1.0.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala, DockerPlugin)

scalaVersion := "2.12.10"

val circeVersion = "0.13.0"
val akkaVersion = "2.5.26"

libraryDependencies ++= Seq(
  ws,
  guice,
  "com.dripower" %% "play-circe"           % "2712.0",
  "io.circe"     %% "circe-core"           % circeVersion,
  "io.circe"     %% "circe-generic"        % circeVersion,
  "io.circe"     %% "circe-generic-extras" % circeVersion,
  "io.circe"     %% "circe-parser"         % circeVersion,
  "com.typesafe.play" %% "play-slick"            % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "mysql"             % "mysql-connector-java"   % "8.0.17",
  "com.typesafe.akka" %% "akka-actor"        % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed"  % akkaVersion,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
  "org.mockito"            % "mockito-core"        % "3.0.0" % Test
)

javaOptions in Universal ++= Seq(
  "-Dpidfile.path=/dev/null"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.weather.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.weather.binders._"
