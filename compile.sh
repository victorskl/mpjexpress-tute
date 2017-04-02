#!/bin/bash

module load Java
module load mpj

javac -cp .:$MPJ_HOME/lib/mpj.jar $1.java
