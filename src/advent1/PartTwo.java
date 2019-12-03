package advent1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartTwo {
    /*
    The Tyranny of the Rocket Equation
     */
    public static void main(String[] args) {
        String tempLine;
        int mass;
        int sumFuel = 0;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/advent1/module mass.text"))) {
            while ((tempLine = readTextFile.readLine()) != null) {
                mass = Integer.parseInt(tempLine);
                sumFuel += getRequiredFuel(mass);
            }

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }

        System.out.println("total fuel of all module masses: " + sumFuel);
    }

    public static int getRequiredFuel(int moduleMass) {
        int tempFuel;
        int fuelNeeded = 0;

        while (true) {
            tempFuel = (moduleMass / 3) - 2;
            if (tempFuel <= 0) {
                return fuelNeeded;
            }
            fuelNeeded += tempFuel;
            moduleMass = tempFuel;
        }
    }
}
