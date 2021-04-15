cd ./thebetterway/
ecj -cp ../lib/kotlin-compiler.jar CompilerServer.java
jar -cf ../bin/compiler-server.jar *.class
