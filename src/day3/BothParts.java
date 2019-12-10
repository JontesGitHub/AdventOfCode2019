package day3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BothParts {
    public static ReadTextFile wireOne = new ReadTextFile();
    public static ReadTextFile wireTwo = new ReadTextFile();

    public static int shortestDistance;
    public static List<String> equalPoints = new ArrayList<>();

    public static void main(String[] args) {

        wireOne.createPathForWire("wire one.txt");
        wireTwo.createPathForWire("wire two.txt");

        partOne();

        partTwo();
    }

    public static void partOne() {

        if (wireOne.getListOfPoints().size() > wireTwo.getListOfPoints().size()) {
            for (int i = 0; i < wireTwo.getListOfPoints().size(); i++) {
                if (wireOne.getListOfPoints().contains(wireTwo.getListOfPoints().get(i))) {
                    equalPoints.add(wireTwo.getListOfPoints().get(i));
                }
            }
        } else {
            for (int i = 0; i < wireOne.getListOfPoints().size(); i++) {
                if (wireTwo.getListOfPoints().contains(wireOne.getListOfPoints().get(i))) {
                    equalPoints.add(wireOne.getListOfPoints().get(i));
                }
            }
        }

        int currentMinDistance = Integer.MAX_VALUE;
        for (String point : equalPoints) {
            int[] tempPoint = Arrays.stream(point.split(",")).mapToInt(Integer::parseInt).toArray();

            if (Math.abs(tempPoint[0]) + Math.abs(tempPoint[1]) < currentMinDistance) {
                shortestDistance = (Math.abs(tempPoint[0]) + Math.abs(tempPoint[1]));
                currentMinDistance = shortestDistance;
            }
        }
        System.out.println("Shortest distance is: " + shortestDistance);
    }

    public static void partTwo() {
        int shortestSteps = Integer.MAX_VALUE;
        for (String equalPoint : equalPoints) {
            int tempWireOneSteps = wireOne.amountOfStepsToClosestIntersection(equalPoint);
            int tempWireTwoSteps = wireTwo.amountOfStepsToClosestIntersection(equalPoint);

            if (shortestSteps > (tempWireOneSteps + tempWireTwoSteps)) {
                shortestSteps = tempWireOneSteps + tempWireTwoSteps;
            }
        }
        System.out.println("Total steps: " + shortestSteps);
    }
}
