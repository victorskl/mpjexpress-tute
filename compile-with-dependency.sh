#!/bin/bash

module load Java
module load mpj

javac -cp .:./lib/gson-2.8.0.jar:$MPJ_HOME/lib/mpj.jar $1
