
val akkaVersion = "2.5.3"

val akkaHttpVersion = "10.0.9"


val akka = Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion
)


val consul = Seq(
  "com.orbitz.consul" % "consul-client" % "0.16.5"
)

val myProject = Project("nginx-consule", file("."))
  .settings(
    fork in run := true,
    assemblyJarName in assembly := "app.jar",
    scalaVersion := "2.12.3",
    scalacOptions ++= Seq(
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:postfixOps",
      "-encoding", "UTF-8",
      "-deprecation",
      "-unchecked"
    ),
    libraryDependencies ++= consul ++ akka
  )

