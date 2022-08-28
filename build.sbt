ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "spt.web.crawler"
ThisBuild / organizationName := "example"

val Versions = new {
  val zio = "2.0.1"
}

lazy val root = (project in file("."))
  .settings(
    name := "spt-web-crawler",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % Versions.zio,
      "dev.zio" %% "zio-test" % Versions.zio % Test
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
