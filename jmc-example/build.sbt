
val akkaVersion = "2.4.17"

val myProject = Project("jmc-example", file("."))
.settings(
  scalaVersion := "2.11.8",
  assemblyJarName in assembly := "example.jar",
  javaOptions ++= Seq(
    "-XX:+UnlockCommercialFeatures",
    "-XX:+FlightRecorder",
    "-XX:+UnlockDiagnosticVMOptions",
    "-XX:+LogCompilation"
  ),
  scalacOptions ++= Seq(
    "-feature",
    "-encoding", "UTF-8",
    "-deprecation",
    "-unchecked"
  ),
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.typesafe.play" %% "play-json" % "2.5.13"
  )
)

