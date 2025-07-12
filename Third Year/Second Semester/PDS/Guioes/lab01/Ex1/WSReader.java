import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class WSReader {

    String WSFile;
    ArrayList<String> Words = new ArrayList<String>();
    ArrayList<String> SopaBD = new ArrayList<String>();

    public WSReader(String filename) {
        this.WSFile = filename;
        this.SopaBD = EliminaComentarios(filename, this.SopaBD);

    }

    public void ReadFile(String Ficheiro) {

        
        if (!Verify15x15(Ficheiro)) {
            System.err.println("Erro: O ficheiro não tem 15 linhas");
            System.exit(1);
        }

        if (!LetrasMinusculas(Ficheiro)) {
            System.err.println("Erro: A sopa de letras contém letras maiúsculas");
            System.exit(1);

        }

        if (!firstMaiuscula(Ficheiro, Words)) {
            System.err.println("Erro: A primeira letra de uma palavra não é maiúscula");
            System.exit(1);

        }

        if (!OnlyAlphabetic(Words)) {
            System.err.println("Erro: A sopa de letras contém caracteres não alfabéticos");
            System.exit(1);

        }


    }

    public boolean Verify15x15(String filename) {
        if (SopaBD.size() != 15) {
            return false;
        }
        for (String line : SopaBD) {
            if (line.length() != 15) {
                return false;
            }
        }
        return true;
    }

    public boolean LetrasMinusculas(String filename) {
        for (String line : SopaBD) {
            if (!line.matches("[a-z]+")) {
                return false;
            }
        }
        return true;
    }

    public boolean firstMaiuscula(String filename, ArrayList<String> Words) {
        for (String word : Words) {
            System.out.println(word);
            if (!Character.isUpperCase(word.charAt(0))) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> EliminaComentarios(String filename, ArrayList<String> SopaBD) {
        ArrayList<String> sopaSemComentários = new ArrayList<>();
        try (Scanner myReader = new Scanner(new File(filename))) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().split("#")[0].strip();
                while (!data.contains(";")) {
                    if (!data.isEmpty() && !Character.isUpperCase(data.charAt(0))) {
                        sopaSemComentários.add(data);
                    }
                    if (myReader.hasNextLine()) {
                        data = myReader.nextLine().split("#")[0].strip();
                    } else {
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
        return sopaSemComentários;
    }

    public boolean OnlyAlphabetic(ArrayList<String> Words) {
        for (String word : Words) {
            if (!word.matches("[A-Za-z]+"))
                return false;
        }
        return true;
    }

    public ArrayList<String> GetWords(String filename) {
        ArrayList<String> Words = new ArrayList<>();
        boolean isReadingWords = false;

        try (Scanner myReader = new Scanner(new File(filename))) {
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine().trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (line.matches(".*[A-Z].*") && line.contains(";")) {
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

        return Words;
    }

    public ArrayList<String> NoBlanckLines(String filename, ArrayList<String> SopaBD) {

        for (String line : SopaBD) {
            if (line.isEmpty()) {
                SopaBD.remove(line);
            }
        }

        return SopaBD;
    }

    public ArrayList<String> GetSopaBD() {
        return SopaBD;
    }

}
