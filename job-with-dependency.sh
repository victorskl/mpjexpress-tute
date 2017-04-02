#!/bin/bash

#SBATCH --time=00:00:59
#SBATCH --nodes=2
#SBATCH --ntasks=4
#SBATCH --cpus-per-task=1

module load Java
module load mpj

mpjrun.sh -cp lib/gson-2.8.0.jar -np 4 HelloJson
