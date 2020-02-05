package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PartOneDay7 {
    int pointerPosition;
    int[] intCodeArray;
    int maxThrusterSignal = 0;
    int output = 0;
    boolean isTimeToSwitch = false;

    public PartOneDay7() {
        String[] stringArray;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/day7/puzzle input.txt"))) {
            stringArray = readTextFile.readLine().split(",");
            intCodeArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }
    }

    public void startIntCode(int[] inputArr) {

        for (int n : inputArr) {
            runNextInstruction(n);

            while (!isTimeToSwitch) {
                runNextInstruction(output);
            }
            isTimeToSwitch = false;
            pointerPosition = 0;

            if (output > maxThrusterSignal) {
                maxThrusterSignal = output;
            }
        }
    }

    public void runNextInstruction(int input) {
        int opcode = intCodeArray[pointerPosition];
        PartOneDay7.Instruction instruction = PartOneDay7.Instruction.findfirstDigit(opcode);
        int outputPosition;


        switch (instruction) {
            case ADD:
            case MULTIPLY:
            case EQUALS:
            case LESSTHAN:
                outputPosition = pointerPosition + 3;
                intCodeArray[intCodeArray[outputPosition]] = getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
                pointerPosition += 4;
                break;
            case INPUT:
                outputPosition = pointerPosition + 1;
                intCodeArray[intCodeArray[outputPosition]] = input;
                pointerPosition += 2;
                break;
            case OUTPUT:
                String opcodeInString = String.valueOf(opcode);
                if (opcodeInString.length() == 3) {
                    if (opcodeInString.substring(0, 1).equals("1")) {
                        System.out.println(intCodeArray[pointerPosition + 1]);
                        output = intCodeArray[pointerPosition + 1];
                    } else {
                        System.out.println(intCodeArray[intCodeArray[pointerPosition + 1]]);
                        output = intCodeArray[intCodeArray[pointerPosition + 1]];
                    }
                } else if (opcodeInString.length() == 4) {
                    if (opcodeInString.substring(1, 2).equals("1")) {
                        System.out.println(intCodeArray[pointerPosition + 1]);
                        output = intCodeArray[pointerPosition + 1];
                    } else {
                        System.out.println(intCodeArray[intCodeArray[pointerPosition + 1]]);
                        output = intCodeArray[intCodeArray[pointerPosition + 1]];
                    }
                } else {
                    System.out.println(intCodeArray[intCodeArray[pointerPosition + 1]]);
                    output = intCodeArray[intCodeArray[pointerPosition + 1]];
                }
                pointerPosition += 2;
                break;
            case JUMP_IF_TRUE:
                getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
                break;
            case JUMP_IF_FALSE:
                getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
                break;
            case NEXTAMPLIFIER:
                isTimeToSwitch = true;
                break;
            default:
                throw new IllegalStateException("Unsupported opcode: " + instruction + " at instruction pointer " + pointerPosition);
        }

    }

    public int getTrueValue(int opcode, int value1, PartOneDay7.Instruction instruction, int value2) {
        int verb = 0;
        int noun = 0;
        String opcodeInString = String.valueOf(opcode);

        if (opcodeInString.length() <= 2) {
            verb = intCodeArray[intCodeArray[value1]];
            noun = intCodeArray[intCodeArray[value2]];
        } else if (opcodeInString.length() == 3) {
            if (opcodeInString.substring(0, 1).equals("1")) {
                verb = intCodeArray[value1];
            } else {
                verb = intCodeArray[intCodeArray[value1]];
            }
            noun = intCodeArray[intCodeArray[value2]];

        } else if (opcodeInString.length() == 4) {
            if (opcodeInString.substring(1, 2).equals("1")) {
                verb = intCodeArray[value1];
            } else {
                verb = intCodeArray[intCodeArray[value1]];
            }
            if (opcodeInString.substring(0, 1).equals("1")) {
                noun = intCodeArray[value2];
            } else {
                noun = intCodeArray[intCodeArray[value2]];
            }
        }

        if (instruction == PartOneDay7.Instruction.ADD) {
            return verb + noun;
        } else if (instruction == PartOneDay7.Instruction.MULTIPLY) {
            return verb * noun;
        } else if (instruction == PartOneDay7.Instruction.JUMP_IF_TRUE) {
            if (verb != 0) {
                pointerPosition = noun;
                return 1;
            } else {
                pointerPosition += 3;
            }
        } else if (instruction == PartOneDay7.Instruction.JUMP_IF_FALSE) {
            if (verb == 0) {
                pointerPosition = noun;
            } else {
                pointerPosition += 3;
            }
        } else if (instruction == PartOneDay7.Instruction.LESSTHAN) {
            if (verb < noun) {
                return 1;
            }
        } else if (instruction == PartOneDay7.Instruction.EQUALS) {
            if (verb == noun) {
                return 1;
            }
        }
        return 0;
    }

    private enum Instruction {
        ADD(1),
        MULTIPLY(2),
        INPUT(3),
        OUTPUT(4),
        JUMP_IF_TRUE(5),
        JUMP_IF_FALSE(6),
        LESSTHAN(7),
        EQUALS(8),
        NEXTAMPLIFIER(9);

        int opcode;

        Instruction(int opcode) {
            this.opcode = opcode;
        }

        public static PartOneDay7.Instruction findfirstDigit(int opcode) {
            String opcodeString = String.valueOf(opcode);
            String firstDigit = opcodeString.length() > 1 ? opcodeString.substring(opcodeString.length() - 1) : opcodeString;
            int toint = Integer.parseInt(firstDigit);

            if (toint == 1) {
                return PartOneDay7.Instruction.ADD;
            } else if (toint == 2) {
                return PartOneDay7.Instruction.MULTIPLY;
            } else if (toint == 3) {
                return PartOneDay7.Instruction.INPUT;
            } else if (toint == 4) {
                return PartOneDay7.Instruction.OUTPUT;
            } else if (toint == 5) {
                return PartOneDay7.Instruction.JUMP_IF_TRUE;
            } else if (toint == 6) {
                return PartOneDay7.Instruction.JUMP_IF_FALSE;
            } else if (toint == 7) {
                return PartOneDay7.Instruction.LESSTHAN;
            } else if (toint == 8) {
                return PartOneDay7.Instruction.EQUALS;
            } else if (toint == 9) {
                return Instruction.NEXTAMPLIFIER;
            }
            return null;
        }
    }

}

class Main {
    static int maxSignal = 0;

    public static void main(String[] args) {
        Permutation createPermut = new Permutation("01234");

        for (String combination : createPermut.permutList) {
            PartOneDay7 p = new PartOneDay7();
            p.startIntCode(Arrays.stream(combination.split("")).mapToInt(Integer::parseInt).toArray());
            if (maxSignal < p.maxThrusterSignal) {
                maxSignal = p.maxThrusterSignal;
            }
        }
        System.out.println("largest signal is: " + maxSignal);
    }
}
