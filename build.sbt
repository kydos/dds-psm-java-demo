
version := "1.0"

organization := "org.omg"

scalaVersion := "2.10.0-RC3"

resolvers += "kydos-repo" at "http://kydos.github.com/maven/releases"

libraryDependencies += "junit" % "junit" % "4.8" % "test"

libraryDependencies += "org.omg" % "dds-psm-java_2.10.0-RC3" % "1.0"

homepage :=  Some(new java.net.URL("http://github/kydos/dds-psm-java"))

autoCompilerPlugins := true

scalacOptions += "-deprecation"

scalacOptions += "-unchecked"

scalacOptions += "-optimise"

scalacOptions += "-feature"

scalacOptions += "-language:postfixOps"

javacOptions += "-Xlint:unchecked"

