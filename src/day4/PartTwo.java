package day4;

import java.util.HashMap;
import java.util.Map;

public class PartTwo {
    public static int smallestNR = 125730;
    public static int largestNR = 579381;

    public static void main(String[] args) {
        int possiblePasswords = 0;

        for (int i = smallestNR; i <= largestNR; i++) {
            Map<Integer, Integer> adjacentDigitslist = new HashMap<Integer, Integer>();

            for (int j = 0; j < 5; j++) {
                int currDigit = Integer.parseInt(Integer.toString(i).substring(j, j + 1));
                int nextDigit;
                if (j == 4) {
                    nextDigit = Integer.parseInt(Integer.toString(i).substring(j + 1));
                } else {
                    nextDigit = Integer.parseInt(Integer.toString(i).substring(j + 1, j + 2));
                }

                if (currDigit > nextDigit) {
                    break;
                }
                if (currDigit == nextDigit) {
                    if (adjacentDigitslist.containsKey(currDigit)) {
                        for(Integer key : adjacentDigitslist.keySet()) {
                            if (key == currDigit) {
                                int value = adjacentDigitslist.get(key);
                                value++;
                                adjacentDigitslist.remove(key);
                                adjacentDigitslist.put(key, value);
                            }
                        }
                    } else {
                        adjacentDigitslist.put(currDigit, 1);
                    }

                }
                if (j == 4) {
                    if (!adjacentDigitslist.isEmpty()) {
                        for (Integer value : adjacentDigitslist.values()) {
                            System.out.println(value);
                            if (value == 1) {
                                possiblePasswords++;
                                break;
                            }
                        }
                    }
                }

            }
        }
        System.out.println("Amount of possible passwords: " + possiblePasswords);

    }
}
