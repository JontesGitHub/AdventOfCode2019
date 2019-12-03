package advent2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PartOne {
    /*
    1202 Program Alarm puzzle
     */

    public static void main(String[] args) {
        String tempLine;
        String[] stringArray;
        int[] intCodeArray = null;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/advent2/Intcode.txt"))) {
            stringArray = readTextFile.readLine().split(",");
            intCodeArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }

        for (int i = 0; i < intCodeArray.length; i+=4) {
            int opcode = intCodeArray[i];

            if (opcode == 1 || opcode == 2 || opcode == 99) {
                int input1 = intCodeArray[i + 1];
                int input2 = intCodeArray[i + 2];
                int output = intCodeArray[i + 3];

                switch (opcode) {
                    case 1:
                        intCodeArray[output] = intCodeArray[input1] + intCodeArray[input2];
                        break;
                    case 2:
                        intCodeArray[output] = intCodeArray[input1] * intCodeArray[input2];
                        break;
                    case 99:
                        System.out.println("Value at position 0: " + intCodeArray[0]);
                        System.exit(0);
                }

            } else {
                System.out.println("something went wrong");
            }

        }

    }

}
