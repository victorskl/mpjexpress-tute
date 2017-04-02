#!/bin/bash

#SBATCH --time=00:00:59
#SBATCH --nodes=2
#SBATCH --ntasks=4
#SBATCH --cpus-per-task=1

module load Java
module load mpj

mpjrun.sh -np 4 HelloWorld
