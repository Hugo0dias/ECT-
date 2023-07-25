package aula11;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class FileReader {

    public static void main(String[] args) {

        try {

            File file = new File("texto.txt");
            Scanner s = new Scanner(file);
            s.useDelimiter("\t\n.,:';?!-*{}=+&/()[]”“\"\'");
            TreeMap<String, TreeMap<String, Integer>> conj_palavras = new TreeMap<>();
            String word;
            String anterior = null;
            while (s.hasNext()) {

                word = s.next().toLowerCase();
                if (word.length() > 2) {

                    if (conj_palavras.get(word) == null) {

                        conj_palavras.put(word, new TreeMap<String, Integer>());
                    }

                    if (conj_palavras.get(word) != null) {
                        TreeMap<String, Integer> par = conj_palavras.get(anterior);
                        if (par.get(anterior) == null) { // lê a primeira palavra
                            par.put(word, 1); // coloca a palavra que aparece depois da guardada em anterior com o valor
                                              // 1 como key
                        } else {
                            int valor = par.get(anterior);
                            par.put(anterior, valor + 1);
                        }
                    }
                    anterior = word;

                }

            }

            System.out.println(conj_palavras);

        } catch (FileNotFoundException e) {
            System.out.println("Nao encontrado");
        }

    }

}
