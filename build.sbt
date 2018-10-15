name := """mutants"""
organization := "mutants_org"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += javaJdbc
libraryDependencies += "com.h2database" % "h2" % "1.4.192"
libraryDependencies ++= Seq(
  javaJpa,
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
)
libraryDependencies += "org.mockito" % "mockito-core" % "2.10.0" % "test"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "mutants_org.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "mutants_org.binders._"

PlayKeys.externalizeResources := false
