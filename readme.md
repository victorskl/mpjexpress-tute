## Notes for Windows

Download MPJ Express, extract package, setup `MPJ_HOME` environment variable. Just follow the guide.

* http://mpj-express.org/docs/guides/windowsguide.pdf

### Build and Run on Local Development

* Open `cmd.exe`
* `build HelloWorld`
* `run 4 HelloWorld`

## Notes for Linux/SLURM HPC Cluster

All bash shell scripts are written for [Spartan cluster](https://dashboard.hpc.unimelb.edu.au/) in mind! But it may still apply for similar Linux/SLURM HPC setup.

* Compile by `bash compile.sh HelloWorld.java`
* Submit job by `sbatch job.sh`

For Java program with external library dependency:

* Compile by `bash compile-with-dependency.sh HelloJson.java`
* Submit job by `sbatch job-with-dependency.sh`

## Notes for External Library Dependencies

To compile with a particular library, you provides the classpath `-cp` flag as follow:

### On Windows

```batch
javac -cp .;./lib/gson-2.8.0.jar;%MPJ_HOME%/lib/mpj.jar HelloJson.java
mpjrun.bat -cp lib/gson-2.8.0.jar -np 4 HelloJson
```

### On Linux
```bash
javac -cp .:./lib/gson-2.8.0.jar:$MPJ_HOME/lib/mpj.jar HelloJson.java
mpjrun.sh -cp lib/gson-2.8.0.jar -np 4 HelloJson
```

### Compile Multiple Class Files
If your project have a handful of class files to compile, then use asterisk.

```batch
javac -cp .;./lib/gson-2.8.0.jar;%MPJ_HOME%/lib/mpj.jar *.java
```

### Many Dependencies
If your project have many dependencies (i.e. using handful of libraries) then you should consider [using Maven](https://github.com/victorskl/mpj-maven). This is the prefer way of setting up a good practice Java project!

## Notes for DataTypeExample

[DataTypeExample.java](DataTypeExample.java) is to test the idea on decomposing Java Map/HashMap data structure to MPI friendly primitive data type and array, sending back and ford while using MPI Scatter, Gather and Reduce routines. Assumption is, we can retain the order of Map's key. To study the code, build and run with small values to observe.
 
```batch
build DataTypeExample
run 2 DataTypeExample
```

Example output: note that Data Char Array is randomized each invocation.
```batch
Data Char Array: [ A C D C C A A A D B A B A C A C C A D C ]

Rank 0 received 10 elements.[A, C, D, C, C, A, A, A, D, B]
Rank 1 received 10 elements.[A, B, A, C, A, C, C, A, D, C]
Rank 1 occurrence Map: [A, B, C, D] [4, 1, 4, 1]
Rank 0 occurrence Map: [A, B, C, D] [4, 1, 3, 2]
Rank 0 sorted Map: [A, B, C, D] [4, 1, 3, 2]
Rank 1 sorted Map: [A, B, C, D] [4, 1, 4, 1]
Rank 0 decomposed array: [4, 1, 3, 2]
Rank 1 decomposed array: [4, 1, 4, 1]
Occurrence of character [A, B, C, D] is: [8, 2, 7, 3] respectively!

Sort by highest character occurrence count:
A:8
C:7
D:3
B:2
```

---
Clone and read in how things are done in details!