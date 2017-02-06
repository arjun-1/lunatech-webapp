name := """lunatech-webapp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
 

scalaVersion := "2.11.8"

libraryDependencies += javaJpa

libraryDependencies += cache

// libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"

// libraryDependencies += "org.xerial" % "sqlite-jdbc" % "3.8.10.1"

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.2.5.Final"


libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0"

// libraryDependencies += javaWs % "test"

// libraryDependencies += "org.hibernate" % "hibernate-core" % "5.2.5.Final"
