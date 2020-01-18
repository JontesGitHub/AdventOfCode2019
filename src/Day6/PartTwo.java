package Day6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PartTwo {
    Map<String, String> orbitsList = new HashMap<>();
    List<String> YOUandSANtoCOM = new ArrayList<>();

    public PartTwo() {
        String temprow;
        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/Day6/puzzle input.txt"))) {
            while ((temprow = readTextFile.readLine()) != null) {
                String orbits = temprow.substring(0, 3);
                String object = temprow.substring(4);
                orbitsList.put(object, orbits);
            }
        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }
        calculateOrbits("YOU");
        calculateOrbits("SAN");

        int nr = (int) YOUandSANtoCOM.stream().distinct().count() - (YOUandSANtoCOM.size() - (int) YOUandSANtoCOM.stream().distinct().count());
        System.out.println(nr);
    }

    public void calculateOrbits(String object) {
        while (orbitsList.containsKey(object)) {
            object = orbitsList.get(object);
            YOUandSANtoCOM.add(object);
        }
    }


    public static void main(String[] args) {
        PartTwo p = new PartTwo();
    }
}
