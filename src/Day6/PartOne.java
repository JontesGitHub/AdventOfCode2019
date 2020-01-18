package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PartOne {
    Map<String, String> orbitsList = new HashMap<>();

    public PartOne() {
        String temprow;
        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/Day6/puzzle input.txt"))) {
            while ((temprow = readTextFile.readLine()) != null) {
                String orbits = temprow.substring(0, 3);
                String object = temprow.substring(4);
                orbitsList.put(object, orbits);
            }
            int orbitCount = 0;
            for (String obj : orbitsList.values()) {
                orbitCount++;
                while (orbitsList.containsKey(obj)) {
                    obj = orbitsList.get(obj);
                    orbitCount++;
                }
            }
            System.out.println(orbitCount);

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PartOne p = new PartOne();
    }
}
