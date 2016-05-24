name := "scalafiddle"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.github.unterstein" %% "play-rest-plugin" % "0.1.0",
  "org.webjars" % "webjars-play_2.11" % "2.5.0-2",
  "org.webjars" % "bootstrap" % "3.3.6",
  "org.webjars" % "bootswatch-spacelab" % "3.3.5+4",
  "org.webjars" % "jquery" % "2.2.4",
  "org.webjars" % "knockout" % "3.4.0",
  "org.webjars.bower" % "knockout-mapping" % "2.4.1",
  "org.webjars.bower" % "knockout-validation" % "2.0.3" exclude("org.webjars.bower", "knockout"),
  "org.webjars" % "font-awesome" % "4.6.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "unterstein.github.io" at "http://unterstein.github.io/repo"

