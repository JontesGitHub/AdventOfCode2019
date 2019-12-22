package Day5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class PartTwo {
    int pointerPosition;
    int[] intCodeArray;

    public PartTwo() {
        String[] stringArray;

        try (BufferedReader readTextFile = new BufferedReader(new FileReader("src/day5/puzzle input.txt"))) {
            stringArray = readTextFile.readLine().split(",");
            intCodeArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();

        } catch (IOException e) {
            System.out.println("Catch: " + e.getMessage());
        }

        while (intCodeArray[pointerPosition] != 99) {
            runNextInstruction();
        }

    }

    public void runNextInstruction() {
        int opcode = intCodeArray[pointerPosition];
        Instruction instruction = Instruction.findfirstDigit(opcode);
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
                intCodeArray[intCodeArray[outputPosition]] = 5;
                pointerPosition += 2;
                break;
            case OUTPUT:
                String opcodeInString = String.valueOf(opcode);
                if (opcodeInString.length() == 3) {
                    if (opcodeInString.substring(0, 1).equals("1")) {
                        System.out.println(intCodeArray[pointerPosition + 1]);
                    } else {
                        System.out.println(intCodeArray[intCodeArray[pointerPosition + 1]]);
                    }
                } else if (opcodeInString.length() == 4) {
                    if (opcodeInString.substring(1, 2).equals("1")) {
                        System.out.println(intCodeArray[pointerPosition + 1]);
                    } else {
                        System.out.println(intCodeArray[intCodeArray[pointerPosition + 1]]);
                    }
                } else {
                    System.out.println(intCodeArray[intCodeArray[pointerPosition + 1]]);
                }
                pointerPosition += 2;
                break;
            case JUMP_IF_TRUE:
                getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
                break;
            case JUMP_IF_FALSE:
                getTrueValue(opcode, pointerPosition + 1, instruction, pointerPosition + 2);
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
        EQUALS(8);

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
            }
            return null;
        }
    }

    public static void main(String[] args) {
        PartTwo p = new PartTwo();
    }
}
