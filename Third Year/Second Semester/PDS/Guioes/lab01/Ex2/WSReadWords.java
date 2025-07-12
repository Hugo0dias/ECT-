import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WSReadWords {
  
    String WSFile;
    ArrayList<String> Words = new ArrayList<>();

    public WSReadWords(String filename) {
        this.WSFile = filename;
    }

    public ArrayList<String> GetWords() {
        
        boolean isReadingWords = false;

        try (Scanner myReader = new Scanner(new File(this.WSFile))) {
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.matches(".*[A-Z].*")) {
                    isReadingWords = true;
                }

                if (isReadingWords) {
                    String[] elements = line.split("[;,\\s]+");
                    for (String element : elements) {
                        element = element.trim();
                        if (!element.isEmpty() && Character.isUpperCase(element.charAt(0))) {
                            if (element.length() < 3) {
                                System.err.println(
                                        "Erro: A Lista de palavras a encontrar contém palavras com menos de 3 letras\"");
                                System.exit(1);
                            }
                            Words.add(element);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
        }

        //System.out.println("Palavras encontradas: " + Words);
        return Words;
    }
    
}
