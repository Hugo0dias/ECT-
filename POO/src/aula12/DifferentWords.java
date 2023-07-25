package aula12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class DifferentWords {

    public static void main(String[] args) throws FileNotFoundException {

        File file = new File("DifferentWords.txt");
        Scanner input = new Scanner(file);
        String word;
        Set<String> Palavras_diferentes = new TreeSet<>();
        ArrayList<String> Palavras_iguais = new ArrayList<>();

        while (input.hasNext()) {

            word = input.next().toLowerCase();
            System.out.println(word);
            Palavras_diferentes.add(word);
            Palavras_iguais.add(word);

        }

        System.out.println(Palavras_diferentes.size());
        System.out.println(Palavras_iguais.size());

        input.close();
    }

}
