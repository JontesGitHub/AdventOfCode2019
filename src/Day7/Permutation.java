package Day7;

import java.util.ArrayList;
import java.util.List;

public class Permutation {
    List<String> permutList = new ArrayList<>();

    public void printPermutn(String str, String ans) {

        if (str.length() == 0) {
            permutList.add(ans);
            return;
        }

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String ros = str.substring(0, i) + str.substring(i + 1);
            printPermutn(ros, ans + ch);
        }
    }

    public Permutation(String phaseIntegers) {
        String s = phaseIntegers;
        printPermutn(s, "");
    }
}
