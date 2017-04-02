import com.google.gson.*;
import mpi.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class HelloJson {

    public static void main(String args[]) throws Exception {

        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        if (rank == 0) { // Only master read JSON file
            System.out.println("Size is: " + size);
            Gson gson = new Gson();
            try (Reader reader = new FileReader("test.json")) {
                JsonElement json = gson.fromJson(reader, JsonElement.class);
                String jsonInString = gson.toJson(json);
                System.out.println(jsonInString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Hello from rank : " + rank);

        MPI.Finalize();
    }
}
