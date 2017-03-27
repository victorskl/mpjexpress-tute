@echo off

rem javac -cp .;%MPJ_HOME%/lib/mpj.jar HelloWorld.java
rem javac -cp .;%MPJ_HOME%/lib/mpj.jar ToyExample.java

javac -cp .;%MPJ_HOME%/lib/mpj.jar %1.java