import mpi.MPI;

import java.util.*;
import java.util.stream.Collectors;

public class DataTypeExample {

    public static void main(String[] args) {
        new DataTypeExample().start(args);
    }

    /**
     * To get the idea, try run with 2 processors, i.e. 'run.bat 2 DataTypeExample'
     * So that you can visually count the character and verify the result.
     */
    private void start(String[] args) {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        /**
         * Assumption:
         *   consider you have array of data that need to be processed and,
         *   data is equally divisible by N-processor
         *
         * Mock random character array within define alphabet range.
         * Then count occurrence of each character in N-processors parallel-ly!
         */
        final String alphabet = "ABCD";
        char[] data = new char[size * 10];
        int chunkSize = data.length / size;
        char[] localData = new char[chunkSize];

        if (rank == 0) mockData(data, alphabet);

        // Wait for rank 0 to init mock data
        MPI.COMM_WORLD.Barrier();

        MPI.COMM_WORLD.Scatter(data, 0, chunkSize, MPI.CHAR, localData, 0, chunkSize, MPI.CHAR, 0);

        printArray(rank, localData, chunkSize);

        // Say naive trivial way of counting implementation using HashMap for example.
        // The purpose here is, how to pass this counting HashMap data type back to rank 0!
        Map<Character, Integer> occurrenceMap = new HashMap<>();
        occurrenceMap.put('D', 0);
        occurrenceMap.put('B', 0);
        occurrenceMap.put('A', 0);
        occurrenceMap.put('C', 0);
        for (char c : localData) {
            occurrenceMap.put(c, occurrenceMap.get(c) + 1);
        }

        printMap(rank, occurrenceMap, "occurrence");

        // Assume we can sort by key order in natural way.
        // Though it may be seem already sorted, but make sure we sort it. And this is important!
        Map<Character, Integer> sortedMap = new TreeMap<>(occurrenceMap);

        printMap(rank, sortedMap, "sorted");

        // Decompose sorted Map values into 1D array.
        Integer[] values = sortedMap.values().toArray(new Integer[0]);
        System.out.println("Rank "+ rank +" decomposed array: " + Arrays.toString(values));

        // We can try Gather here. But better yet, we have better one, MPI summing Reduce!
        int[] recvbuf = new int[values.length];
        int[] sendbuf = Arrays.stream(values).mapToInt(Integer::intValue).toArray();
        MPI.COMM_WORLD.Reduce(sendbuf, 0, recvbuf, 0, values.length, MPI.INT, MPI.SUM, 0);

        if (rank == 0) {
            System.out.println("Occurrence of character [A, B, C, D] is: " + Arrays.toString(recvbuf) + " respectively!");

            // BUT, wait, I want my Map view!! Re-construct Map with confidence!
            Map<Character, Integer> finalMap = new HashMap<>();
            finalMap.put('A', recvbuf[0]);
            finalMap.put('B', recvbuf[1]);
            finalMap.put('C', recvbuf[2]);
            finalMap.put('D', recvbuf[3]);

            // Sort by highest character occurrence count
            System.out.println(" ");
            System.out.println("Sort by highest character occurrence count:");
            Map<Character, Integer> sortedFinalMap = finalMap.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new
                    ));

            for (Map.Entry<Character, Integer> entry : sortedFinalMap.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }

        MPI.Finalize();
    }

    private void printMap(int rank, Map<Character, Integer> map, String type) {
        System.out.println("Rank " + rank + " " + type + " Map: "
                + Arrays.toString(map.keySet().toArray()) + " " + Arrays.toString(map.values().toArray()));
    }

    private void printArray(int rank, char[] localData, int chunkSize) {
        System.out.println("Rank " + rank + " received " + chunkSize + " elements." + Arrays.toString(localData));
    }

    private void mockData(char[] data, String alphabet) {
        System.out.println(" ");
        final int N = alphabet.length();
        Random r = new Random();
        System.out.print("Data Char Array: [ ");
        for (int i = 0; i < data.length; i++) {
            char c = alphabet.charAt(r.nextInt(N));
            data[i] = c;
            System.out.print(c + " ");
        }
        System.out.println("]");
        System.out.println(" ");
    }
}
