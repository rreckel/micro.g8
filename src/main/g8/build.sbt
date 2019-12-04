import com.typesafe.sbt.packager.docker._
import NativePackagerHelper._

ThisBuild / scalaVersion         := "2.12.8"
ThisBuild / scalacOptions        ++= Seq(
  "-feature",
  "-unchecked",
  "-deprecation",
  "-explaintypes",

  "-Ywarn-unused",
  "-Xlint:_",

  "-Ypartial-unification",
  "-Yrangepos",

  "-language:_")

ThisBuild / version              := "0.0.1-SNAPSHOT"
ThisBuild / organization         := "$organization$"

lazy val gitbranch = sys.env.get("bamboo_planRepository_branch").map(b => "-" + b).getOrElse("")
lazy val buildNumber = sys.env.get("bamboo_buildNumber").map(b => "-b" + b).getOrElse("")

lazy val runJRebel = taskKey[Unit]("Runs the server with JRebel")


lazy val $name$ = (project in file(".")).
  settings(
    name := "$name$",

    resolvers += Resolver.mavenLocal,

    fullRunTask(runJRebel, Compile, "$organization$.$name$.HttpServer"),
    fork in runJRebel := true,
    javaOptions in runJRebel ++= Seq("-noverify", "-agentpath:/Applications/jrebel/lib/libjrebel64.dylib", "-Djavax.net.ssl.trustStore=" + ((baseDirectory in ThisBuild).value / "docker/trustStore").getPath),
    javaOptions in Test ++= Seq("-Djavax.net.ssl.trustStore=" + ((baseDirectory in ThisBuild).value / "docker/trustStore").getPath),

    parallelExecution in Test := false,
    fork in Test := true,
    fork in run := true,

    libraryDependencies ++= Seq(

      // Http Server
      "com.typesafe.akka"     %% "akka-http"                      % "10.1.7",
      "com.typesafe.akka"     %% "akka-slf4j"                     % "2.5.19",      
      "com.typesafe.akka"     %% "akka-stream"                    % "2.5.19",
      "de.heikoseeberger"     %% "akka-http-circe"                % "1.20.1",
      "org.julienrf"          %% "endpoints-akka-http-server"     % "0.9.0",
      "org.julienrf"          %% "endpoints-akka-http-client"     % "0.9.0",

      // Cats & Circe
      "org.typelevel"         %% "cats-core"                      % "2.0.0",
      "org.typelevel"         %% "cats-free"                      % "2.0.0",
      "org.typelevel"         %% "alleycats-core"                 % "2.0.0",
      "org.typelevel"         %% "cats-effect"                    % "2.0.0",

      "io.circe"              %% "circe-core"                     % "0.12.3",
      "io.circe"              %% "circe-generic"                  % "0.12.3",
      "io.circe"              %% "circe-parser"                   % "0.12.3",

      // Config loading
      "com.github.pureconfig" %% "pureconfig"                     % "0.9.1",

      // Logging
      "io.chrisdavenport"     %% "log4cats-slf4j"                 % "1.0.1",
      "org.slf4j"              % "slf4j-api"                      % "1.7.29",
      "ch.qos.logback"         % "logback-classic"                % "1.2.3",

      // Testing
      "org.scalatest"         %% "scalatest"                      % "3.0.5" % "test",
      "com.typesafe.akka"     %% "akka-stream-testkit"            % "2.5.19" % "test",
      "com.typesafe.akka"     %% "akka-http-testkit"              % "10.1.7" % "test"
    ),

    version in Docker := (version in Docker).value.toString + gitbranch + buildNumber,
    packageName in Docker := "$name$",

    dockerRepository := Some("harbor.apps.k8s.ville.vdl.lu"),
    dockerUsername := Some("$name$"),
    dockerUpdateLatest := true,

    dockerExposedPorts := Seq(8080),

    mappings in Docker ++= directory("docker"),

    dockerCommands ++= Seq(
      Cmd("COPY", s"./docker/trustStore \${(defaultLinuxInstallLocation in Docker).value}/trustStore"),
      Cmd("ENV", s"JAVA_OPTS -Djavax.net.ssl.trustStore=\${(defaultLinuxInstallLocation in Docker).value}/trustStore")
    )
  ).enablePlugins(JavaAppPackaging, DockerPlugin)

