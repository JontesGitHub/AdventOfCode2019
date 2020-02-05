package Day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartTwoDay7 {
    int pointerPosition;
    int[] intCodeArray;
    int maxThrusterSignal = 0;
    int output = 0;
    int outputPosition = 0;
    boolean isTimeToSwitch = false;
    boolean finished = false;
//    int amplifierRunning = 1;
//    int ampAposition = 0;
//    int ampBposition = 0;
//    int ampCposition = 0;
//    int ampDposition = 0;
//    int ampEposition = 0;


    public PartTwoDay7() {
        String[] stringArray;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/day7/puzzle input.txt"))) {
            stringArray = readTextFile.readLine().split(",");
            intCodeArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }

    }

    public void startIntCode(int input) {
        while (!isTimeToSwitch && !finished) {
            runNextInstruction(input);
        }
        isTimeToSwitch = false;
    }


    public void runNextInstruction(int input) {
        int opcode = intCodeArray[pointerPosition];
        Instruction instruction = Instruction.findfirstDigit(opcode);


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
                        output = intCodeArray[pointerPosition + 1];
                    } else {
                        output = intCodeArray[intCodeArray[pointerPosition + 1]];
                    }
                } else if (opcodeInString.length() == 4) {
                    if (opcodeInString.substring(1, 2).equals("1")) {
                        output = intCodeArray[pointerPosition + 1];
                    } else {
                        output = intCodeArray[intCodeArray[pointerPosition + 1]];
                    }
                } else {
                    output = intCodeArray[intCodeArray[pointerPosition + 1]];
                }
                isTimeToSwitch = true;
                pointerPosition += 2;
                break;
            case JUMP_IF_TRUE:
                getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
                break;
            case JUMP_IF_FALSE:
                getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
                break;
            case NEXTAMPLIFIER:
                finished = true;
                break;
            default:
                throw new IllegalStateException("Unsupported opcode: " + instruction + " at instruction pointer " + pointerPosition);
        }

    }

    public int getTrueValue(int opcode, int value1, Instruction instruction, int value2) {
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

        if (instruction == Instruction.ADD) {
            return verb + noun;
        } else if (instruction == Instruction.MULTIPLY) {
            return verb * noun;
        } else if (instruction == Instruction.JUMP_IF_TRUE) {
            if (verb != 0) {
                pointerPosition = noun;
                return 1;
            } else {
                pointerPosition += 3;
            }
        } else if (instruction == Instruction.JUMP_IF_FALSE) {
            if (verb == 0) {
                pointerPosition = noun;
            } else {
                pointerPosition += 3;
            }
        } else if (instruction == Instruction.LESSTHAN) {
            if (verb < noun) {
                return 1;
            }
        } else if (instruction == Instruction.EQUALS) {
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

        public static Instruction findfirstDigit(int opcode) {
            String opcodeString = String.valueOf(opcode);
            String firstDigit = opcodeString.length() > 1 ? opcodeString.substring(opcodeString.length() - 1) : opcodeString;
            int toint = Integer.parseInt(firstDigit);

            if (toint == 1) {
                return Instruction.ADD;
            } else if (toint == 2) {
                return Instruction.MULTIPLY;
            } else if (toint == 3) {
                return Instruction.INPUT;
            } else if (toint == 4) {
                return Instruction.OUTPUT;
            } else if (toint == 5) {
                return Instruction.JUMP_IF_TRUE;
            } else if (toint == 6) {
                return Instruction.JUMP_IF_FALSE;
            } else if (toint == 7) {
                return Instruction.LESSTHAN;
            } else if (toint == 8) {
                return Instruction.EQUALS;
            } else if (toint == 9) {
                return Instruction.NEXTAMPLIFIER;
            }
            return null;
        }
    }
}

class MainPartTwo {

    public static void main(String[] args) {
        Permutation createPermut = new Permutation("56789");
        int maxSignal = 0;

        for (String combination : createPermut.permutList) {

            PartTwoDay7 ampA = new PartTwoDay7();
            PartTwoDay7 ampB = new PartTwoDay7();
            PartTwoDay7 ampC = new PartTwoDay7();
            PartTwoDay7 ampD = new PartTwoDay7();
            PartTwoDay7 ampE = new PartTwoDay7();
            int[] intArr = Arrays.stream(combination.split("")).mapToInt(Integer::parseInt).toArray();


            ampA.runNextInstruction(intArr[0]);
            ampB.runNextInstruction(intArr[1]);
            ampC.runNextInstruction(intArr[2]);
            ampD.runNextInstruction(intArr[3]);
            ampE.runNextInstruction(intArr[4]);

            int inputForA = ampE.output;
            while (!ampE.finished && !ampA.finished && !ampB.finished && !ampC.finished && !ampD.finished) {
                ampA.startIntCode(inputForA);
                ampB.startIntCode(ampA.output);
                ampC.startIntCode(ampB.output);
                ampD.startIntCode(ampC.output);
                ampE.startIntCode(ampD.output);
                inputForA = ampE.output;
                if (maxSignal < ampE.output) {
                    maxSignal = ampE.output;
                    System.out.println(maxSignal);
                }
            }
        }
        System.out.println("largest signal is: " + maxSignal);
    }


}

