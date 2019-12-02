package advent1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class PartOne {

    public static void main(String[] args) {
        String tempLine;
        int mass;
        int fuelNeeded;
        int sumFuel = 0;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/advent1/module mass.text"))) {
            while ((tempLine = readTextFile.readLine()) != null) {
                mass = Integer.parseInt(tempLine);
                System.out.println(mass);
                fuelNeeded = (mass / 3) - 2;
                sumFuel += fuelNeeded;
            }

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }

        System.out.println("Sum fuel of all module masses: " + sumFuel);

    }
}
