import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WSSolver {

    public static void main(String[] args) throws IOException {

        // Inicializar sopa e palavras
        try {
            Puzzle soup = new Puzzle();
            WordsToFind words = new WordsToFind();

            // Ler ficheiro
            String fileName = args[0]; 
            File puzzle = new File(fileName);
            // Separar sopa de letras das palavras a encontrar
            try {
                readFile(puzzle, soup, words);
                // Procurar palavras
                findWords(soup, words, fileName);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Error initializing puzzle and words");
            System.exit(1);
        }

    } 

    public static void readFile(File puzzle,Puzzle soup, WordsToFind words) throws FileNotFoundException {

        Character[][] matrix = new Character[soup.getLines()][soup.getCols()];
        //Print matrix

        try (Scanner file = new Scanner(puzzle)) {

            int nLine = 0;

            while (file.hasNextLine()) {
                String line = file.nextLine().split("#")[0].trim(); // Remove comentários e espaços extras

                if (line.isEmpty()) {
                    System.err.println("Inavlid file.");
                    System.exit(1);
                }

                // Verifica se a linha tem exatamente 15 caracteres e todos são letras minúsculas
                if (line.length() == soup.getPuzzleSize() && line.chars().allMatch(Character::isLowerCase)) {
                    for (int i = 0; i < soup.getPuzzleSize(); i++) {
                        matrix[nLine][i] = line.charAt(i);
                    }
                } else if(Character.isUpperCase(line.charAt(0))){
                    String word[] = line.split("[\\,\\;\\' ']");
                    for(String w : word) { // Para cada palavra
                        if(w.length() >= 3 && w.length() <= 15 && Character.isUpperCase(w.charAt(0))) {
                            words.addWord(w);
                        } else {
                            System.out.printf("Invalid word -> %s\n", w);
                        }
                    }
                } else {
                    System.err.println("Invalid line: " + line);
                    System.exit(1);
                }
                nLine++;
            }
            soup.setMatrix(matrix);
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }
    }
    
    public static void findWords(Puzzle puzzle, WordsToFind words, String fileName) throws IOException {
        PrintWriter saveFile = null;

        try {
            // Criar um ficheiro para guardar o resultado
            try (FileWriter file = new FileWriter(fileName + "_result.txt")) {
                saveFile = new PrintWriter(file);
                
                //cópia da sopa de letras
                Character[][] soup = new Character[puzzle.getLines()][puzzle.getCols()];
                for (int i = 0; i < puzzle.getLines(); i++) {
                    for (int j = 0; j < puzzle.getCols(); j++) {
                        soup[i][j] = puzzle.getMatrix()[i][j];
                    }
                }

                puzzle.setResult(soup);
                
                // Procurar as palavras
                for (String word : words.getWords()) {
                    word = word.toLowerCase();
                    for (int i = 0; i < soup.length; i++) {
                        for (int j = 0; j < soup[i].length; j++) {
                            if (soup[i][j] == word.charAt(0)) {
                                Direction direction = checkDirection(soup, word, i, j);
                                if (direction != null) {
                                    String position = (i+1)+","+(j+1);
                                    saveFile.printf("%-15s %-2d %-5s %-7s%n",word, word.length(), position, direction.getDirection());
                                    markLetters(puzzle.getResult(), word, i, j, direction);
                                }
                            }
                        }
                    }
                }

                saveFile.println();
                
                // Substitui as letras não pertencentes a nenhuma palavra por "-"
                for (int row = 0; row < puzzle.getResult().length; row++) {
                    for (int col = 0; col < puzzle.getResult().length; col++) {
                        if (puzzle.getResult()[row][col] != '_') {
                            saveFile.print("_ ");
                        } else {
                            saveFile.printf("%s ", Character.toUpperCase(soup[row][col]));
                        }
                    }
                    saveFile.println();
                }
                System.out.printf("File %s_result.txt created\n", fileName);
            }
        } catch (IOException e) {
            System.out.println("Error creating file");
            System.exit(1);
        }
    }

    private static Direction checkDirection(Character[][] soup, String word, int row, int col) {
        // Verificar se a palavra está na horizontal

        //Left-Right
        if (col + 1 + word.length() <= soup[0].length) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row][col + i] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.RIGHT;
            }
        }
        //Right-Left
        if (col + 1 - word.length() >= 0) {

            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row][col - i] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.LEFT;
            }
        }

        // Verificar se a palavra está na vertical

        //Up-Down
        if (row + 1 + word.length() <= soup.length) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row + i][col] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.DOWN;
            }
        }
        //Down-Up
        if (row + 1 - word.length() >= 0) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row - i][col] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.UP;
            }
        }

        // Verificar se a palavra está na diagonal ()

        //Up-Right
        if (row + 1 - word.length() >= 0 && col + 1 + word.length() <= soup[0].length) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row - i][col + i] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.UPRIGHT;
            }
        }

        //Up-Left
        if (row + 1 - word.length() >= 0 && col + 1 - word.length() >= 0) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row - i][col - i] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.UPLEFT;
            }
        }

        //Down-Right
        if (row + 1 + word.length() <= soup.length && col + 1 + word.length() <= soup[0].length) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row + i][col + i] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.DOWNRIGHT;
            }
        }

        //Down-Left
        if (row + 1 + word.length() <= soup.length && col + 1 - word.length() >= 0) {
            boolean found = true;
            for (int i = 0; i < word.length(); i++) {
                if (soup[row + i][col - i] != word.charAt(i)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return Direction.DOWNLEFT;
            }
        }

        return null;
    }

    private static void markLetters(Character[][] soup, String word, int row, int col, Direction direction) {
        // Marcar as letras da palavra encontrada
        int length = word.length();
        int deltaRow = 0, deltaCol = 0;

        // 0 -> não muda de linha/coluna
        // 1 -> muda de linha/coluna para o sentido positivo
        // -1 -> muda de linha/coluna para o sentido negativo

        deltaRow = Direction.getCord(direction)[0];
        deltaCol = Direction.getCord(direction)[1];

        for (int i = 0; i < length; i++) {
            soup[row][col] = '_';
            row += deltaRow;
            col += deltaCol;
        }
    }
}