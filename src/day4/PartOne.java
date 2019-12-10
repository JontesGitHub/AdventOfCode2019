package day4;

public class PartOne {
    public static int smallestNR = 125730;
    public static int largestNR = 579381;

    public static void main(String[] args) {
        int possiblePasswords = 0;

        for (int i = smallestNR; i <= largestNR; i++) {
            int doubleDigitsAmount = 0;

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
                    doubleDigitsAmount++;
                }
                if (j == 4) {
                    if (doubleDigitsAmount != 0) {
                        possiblePasswords++;
                    }
                }

            }
        }
        System.out.println("Amount of possible passwords: " + possiblePasswords);

    }
}
