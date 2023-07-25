package aula10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Caractere {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String word = sc.nextLine();
        sc.close();

        System.out.println(word.length());

        HashMap<Character, ArrayList<Integer>> CharIndexes = new HashMap<Character, ArrayList<Integer>>();

        for (int n = 0; n < word.length(); n++) {
            char c = Character.toLowerCase(word.charAt(n));
            if (CharIndexes.get(c) != null) {
                ArrayList<Integer> previous_values = CharIndexes.get(c);
                previous_values.add(n);

                CharIndexes.put(c, previous_values);
                // System.out.println(CharIndexes);
                // System.out.println(c + ", " + previous_values);
            } else {
                CharIndexes.put(c, new ArrayList<>(Arrays.asList(n)));

            }
        }
        System.out.println(CharIndexes);

    }

}
