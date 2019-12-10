package day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadTextFile {

    private List<String> listOfPoints = new ArrayList<>();
    private String[] stringArray = null;


    public List<String> getListOfPoints() {
        return listOfPoints;
    }

    public void createPathForWire(String fileName) {
        fileName = "src/day3/" + fileName;
        int tempX = 0;
        int tempY = 0;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader(fileName))) {
            stringArray = readTextFile.readLine().split(",");
        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }

        for (String s : stringArray) {
            String location = s.substring(0, 1);
            int travelDistance = Integer.parseInt(s.substring(1));
            for (int i = 1; i <= travelDistance; i++) {
                switch (location) {
                    case "R":
                        tempX++;
                        break;
                    case "L":
                        tempX--;
                        break;
                    case "U":
                        tempY++;
                        break;
                    case "D":
                        tempY--;
                        break;
                }
                listOfPoints.add(tempX + "," + tempY);
            }
        }
    }

    public int amountOfStepsToClosestIntersection(String closestIntercetion) {
        int stepCount = 0;
        int tempX = 0;
        int tempY = 0;

        for (String s : stringArray) {
            String location = s.substring(0, 1);
            int travelDistance = Integer.parseInt(s.substring(1));
            for (int i = 1; i <= travelDistance; i++) {
                stepCount++;
                switch (location) {
                    case "R":
                        tempX++;
                        break;
                    case "L":
                        tempX--;
                        break;
                    case "U":
                        tempY++;
                        break;
                    case "D":
                        tempY--;
                        break;
                }
                if (closestIntercetion.equals(tempX + "," + tempY)) {
                    return stepCount;
                }
            }
        }
        return 0;
    }

}

