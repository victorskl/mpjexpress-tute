import mpi.*;

public class HelloWorld {

    public static void main(String args[]) throws Exception {

        MPI.Init(args);
        int me = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (me == 0) System.out.println("Size is: " + size);

        System.out.println("Hi from <" + me + ">");

        MPI.Finalize();
    }
}