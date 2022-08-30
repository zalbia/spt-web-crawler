ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "zalbia"

val Versions = new {
  val scalaUri  = "4.0.2"
  val zio       = "2.0.1"
  val zioConfig = "3.0.2"
  val zioHttp   = "2.0.0-RC10"
  val zioJson   = "0.3.0-RC11"
}

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging, UniversalPlugin, DockerPlugin)
  .settings(
    name := "spt-web-crawler",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"          % Versions.zio,
      "dev.zio" %% "zio-config"   % Versions.zioConfig,
      "dev.zio" %% "zio-json"     % Versions.zioJson,
      "dev.zio" %% "zio-test"     % Versions.zio % Test,
      "dev.zio" %% "zio-test-sbt" % Versions.zio % Test,
      "io.d11"  %% "zhttp"        % Versions.zioHttp
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    dockerExposedPorts := Seq(8080),
    dockerBaseImage := "eclipse-temurin:17.0.4.1_1-jre"
  )
