version := "0.0.1"
scalaVersion := "2.10.4"
seq(bintrayResolverSettings:_*)
libraryDependencies += "org.msgpack" %% "msgpack-scala" % "0.6.11"
libraryDependencies += "org.scalanlp" %% "breeze" % "0.10"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.6.4"
libraryDependencies += "org.clapper" %% "grizzled-slf4j" % "1.0.2"

lazy val generate = taskKey[Unit]("Generate a test msgpack dataset")
generate <<= (assembly in Compile) map {
    (jarFile : File) => s"java -jar ${jarFile}" !
}
