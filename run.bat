@echo off

rem Usage: run 2 HelloJson

rem mpjrun.bat -np 4 HelloWorld
rem mpjrun.bat -np 4 ToyExample

mpjrun.bat -cp lib/gson-2.8.0.jar -np %1 %2