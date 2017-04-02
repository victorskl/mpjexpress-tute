import mpi.*;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class HelloJson {

    public static void main(String args[]) throws Exception {

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == 0) { // master read JSON file
            System.out.println("Size is: " + size);
            Gson gson = new Gson();
        }

        System.out.println("Hi from <" + me + ">");

        MPI.Finalize();
    }
}
