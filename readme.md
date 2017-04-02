### Notes for Windows

Download MPJ Express, extract package, setup MPJ_HOME environment variable. Just follow the guide.

* http://mpj-express.org/docs/guides/windowsguide.pdf

### Build and Run on Local Development

* Open `cmd.exe`
* build or run `{class-name}`

E.g.

* `build HelloWorld`
* `run HelloWorld`

### Notes for Linux/SLURM HPC Cluster

All bash shell scripts are written for [Spartan cluster](https://dashboard.hpc.unimelb.edu.au/) in mind! But it may still apply for similar Linux/SLURM HPC setup.

* Compile by `bash compile.sh HelloWorld.java`
* Submit job by `sbatch job.sh`

For Java program with external library dependency:

* Compile by `bash compile-with-dependency.sh HelloJson.java`
* Submit job by `sbatch job-with-dependency.sh`

Clone and read in how things are done in details!