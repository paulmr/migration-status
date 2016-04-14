organization := "com.gu"

name := "migration-status"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.sumologic.elasticsearch" %  "elasticsearch-core" % "1.0.9",
  "com.github.scopt"            %% "scopt"              % "3.4.0",
  "com.typesafe"                %  "config"             % "1.3.0"
)
