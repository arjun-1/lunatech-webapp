name := """lunatech-webapp"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
    javaJpa,
    //"org.hibernate" % "hibernate-entitymanager" % "5.2.5.Final"
    "org.hibernate" % "hibernate-core" % "5.2.5.Final"
    // "mysql" % "mysql-connector-java" % "5.1.36"
)

libraryDependencies += "org.webjars.npm" % "horsey" % "4.2.2"

libraryDependencies += "org.mockito" % "mockito-core" % "2.1.0"

// libraryDependencies += cache