import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class WSGenerator {

    public static void main(String[] args) {

        if (args.length < 4 || args.length > 4) {
            System.out.println("Number of arguments invalid");
            System.exit(1);
        } else {

            // Incializa as variáveis
            String path = "";
            String name = "";

            //System.out.println("args: " + args.length);

            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-w":
                        // Verifica se existe um argumento após -w
                        if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                            path = args[i + 1]; 
                            i++;
                        } else {
                            System.out.println("Erro: -w requer um argumento.");
                            System.exit(1);
                        }
                        break;
                    case "-s":
                        // Verifica se existe um argumento após -s
                        if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                            name = args[i + 1];
                            i++; 
                        } else {
                            System.out.println("Erro: -s requer um argumento.");
                            System.exit(1);
                        }
                        break;
                    default:
                        // Caso o argumento não seja reconhecido
                        System.out.println("Uso correto: java WSGenerator -w <file_words> -s <file_result_name>");
                        System.exit(1);
                }
            }

            // Inicializar Scanner
            try(Scanner sc = new Scanner(new File(path))) {
                Random rand = new Random();
                WordsToFind wordsToFind = readListWordsToFind(path, sc);

                Puzzle puzzle = new Puzzle();
                
                setWordsInPuzzle(wordsToFind, puzzle.getResult(), rand);
                completePuzzle(puzzle.getResult(), rand);

                // print test
                for (int i = 0; i < puzzle.getLines(); i++) {
                    for (int j = 0; j < puzzle.getCols(); j++) {
                        System.out.print(puzzle.getResult()[i][j] + " ");
                    }
                    System.out.println();
                }

                //print array palavras
                for (String word : wordsToFind.getWords()) {
                    System.out.println(word);
                }

                try(FileWriter resultFile = new FileWriter(new File(name))) {
                    writeResultFile(resultFile, name, puzzle.getResult(), wordsToFind.getWords());
                    resultFile.close();
                } catch (Exception e) {
                    System.out.println("Error writing file");
                    System.out.print(e);
                    System.exit(1);
                }
                sc.close();

            } catch (Exception e) {
                System.out.println("Error reading file");
                System.exit(1);
            }
        }    
    }

    public static WordsToFind readListWordsToFind(String path, Scanner sc) {
        try (sc) {
            WordsToFind wordsToFind = new WordsToFind();
            while(sc.hasNextLine()) {
                String line  = sc.nextLine();
                if (line.isEmpty() || line.length() < 3) {
                    continue;
                }
                if (line.matches(".*[ ,;].*")) {
                    String[] words = line.split("[ ,;]+");
                    // Adiciona as palavras
                    for (String word : words) {
                        if(word.length() < 3 || word.length() > 15 || Character.isLowerCase(word.charAt(0))) {
                            System.out.printf("Invalid word -> %s\n", word);
                            continue;
                        }
                        wordsToFind.addWord(word.trim());
                    }
                } else if(line.matches("\\w+")){ // Uma palavra na linha
                    line = line.substring(0, 1).toUpperCase() + line.substring(1).toLowerCase();
                    wordsToFind.addWord(line.trim());
                } else {
                    System.out.printf("Invalid word -> %s\n", line);
                }
            }
            return wordsToFind;
        }

    }

    public static void setWordsInPuzzle(WordsToFind wordsToFind, Character[][] puzzle, Random rand) {
        for (String word : wordsToFind.getWords()) {
            setWordRand(word.toLowerCase(), puzzle, rand);
        }
    }

    private static void setWordRand(String word, Character[][] puzzle, Random rand) {
        // Inicializa as variáveis
        Direction direction;
        int deltaCol = 0;
        int deltaLine = 0;
        int line = 0;
        int col = 0;
        boolean fit = false;
        
        // Gerar a direção da palavra
        while(deltaCol == 0 && deltaLine == 0) {
            deltaCol = rand.nextInt(3) - 1;
            deltaLine = rand.nextInt(3) - 1;
        }
        
        // Gerar a posição inicial da palavra
        // Verificar se a palavra fit na matriz
        while (!fit) {
            line = rand.nextInt(puzzle.length); 
            col = rand.nextInt(puzzle[0].length);
            fit = true;

            for (int i = 0; i < word.length(); i++) {
                int linhaAtual = line + i * deltaLine;
                int colunaAtual = col + i * deltaCol;

                //System.out.println("linhaAtual: " + linhaAtual + " colunaAtual: " + colunaAtual);

                // Verificar se ultrapassa os limites
                if (linhaAtual < 0 || linhaAtual >= puzzle.length || 
                    colunaAtual < 0 || colunaAtual >= puzzle[0].length) {
                    fit = false;
                    break; 
                }

                // Evitar que palavras sejam sobrepostas
                if (puzzle[linhaAtual][colunaAtual] != '-' && puzzle[linhaAtual][colunaAtual] != word.charAt(i)) {
                    fit = false;
                    break;
                }
            }
        }

        direction = Direction.getDirection(deltaLine, deltaCol);

        // Adiciona a palavra na matriz
        setWord(word, puzzle, direction, line, col);
    }


    private static void setWord(String word, Character[][] puzzle, Direction direction, int line, int col) {
        // Coloca a palavra na matriz de acordo com a direção e a posição inicial
        for(int i = 0; i < word.length(); i++) {
            puzzle[line][col] = word.charAt(i);
            line = line + Direction.getCord(direction)[0];
            col = col + Direction.getCord(direction)[1];
        }
    }

    private static void completePuzzle(Character[][] puzzle, Random rand) {
        // Completa a matriz com letras aleatórias
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j] == '-') {
                    puzzle[i][j] = (char) ('a' + rand.nextInt(26));
                }
            }
        }
    }

    private static void writeResultFile(FileWriter file, String filename, Character[][] puzzle, Set<String> wordsList) throws IOException {
        for (Character[] line : puzzle) {
            for (Character i : line) {
                file.write(i);
            }
            file.write("\n");
        }


        for(String word : wordsList) {
            file.write(word + "\n");
        }

        System.out.println("");
        System.out.printf("File %s created successfully\n", filename);
    }

}