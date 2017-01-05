import sbt._
import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._
import com.twitter.scrooge.ScroogeSBT

object AppBuild extends Build {
  import com.twitter.scrooge._
  import ScroogeSBT._
  import ScroogeSBT._
  import ScroogeSBT.autoImport._

  val setting = Seq(
    scalacOptions ++= Seq("-Xlog-free-terms", "-deprecation", "-feature",
      "-encoding", "UTF-8",
      "-feature",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:postfixOps",
      "-unchecked"
    ),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    resolvers += Resolver.sonatypeRepo("releases"),
    resolvers += "twitter" at "https://maven.twttr.com",
    scalaVersion := "2.11.8"
  )

  val finagleVersion = "6.40.0"

  lazy val myProject = Project(
      id = "example",
      base = file("."),
      settings = setting
    )
    .settings(
      scroogeThriftSourceFolder in Compile <<= baseDirectory {
        base => base / "thrift"
      }
    )
    .settings(
      libraryDependencies ++= Seq(
        "org.apache.thrift" % "libthrift" % "0.9.1",
        "com.twitter" %% "util-collection" % "6.34.0",
        "com.twitter" %% "scrooge-core" % "4.6.0",
        "commons-collections" % "commons-collections" % "3.2.1",
        "com.twitter" %% "finagle-thrift" % finagleVersion,
        "com.twitter" %% "finagle-http" % finagleVersion,
        "com.twitter" %% "finagle-core" % finagleVersion,
        "com.twitter" %% "finagle-thriftmux" % finagleVersion,
        "com.twitter" %% "finagle-serversets" % finagleVersion,
        "com.twitter" %% "finagle-zipkin" % finagleVersion,
        "io.zipkin.finagle" %% "zipkin-finagle" % "0.3.2",
        "io.zipkin.finagle" %% "zipkin-finagle-http" % "0.3.2"
      ).map(_ .exclude("org.slf4j", "slf4j-jdk14")
        .exclude("org.slf4j", "slf4j-log4j12"))
    )



}