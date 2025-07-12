import java.io.*;
import java.util.ArrayList;

public class WSSolver {

    private static char[][] puzzle = new char[15][15];
    private static boolean[][] foundPositions = new boolean[15][15];

    private static ArrayList<String> listaWords = new ArrayList<>();
    private static ArrayList<String> ListWords = new ArrayList<>();

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Usage: java WSSolver <filename>");
            System.exit(0);
        }
        String arg = args[0];
        if ((arg.contains("./Testes/"))){
            System.out.println("The input must be like this: ");
            System.out.println("Usage: java WSSolver <filename.txt>");
            System.exit(0);
        }
        String filename = "./Testes/" + arg;
        WSReader reader = new WSReader(filename);

        reader.ReadFile(filename);
        ListWords = reader.GetWords(filename);

        ListWords = wordContainingWord(ListWords);


        fillPuzzle(reader.GetSopaBD());

        String table = "";
        for (String word : ListWords) {
            table += findWord(word);
        }

        System.out.println("\n");

        char[][] sol = printPuzzleSolution();
        
        //write in file

        try {
            PrintWriter myWriter = new PrintWriter(new FileWriter(filename.substring(0, filename.lastIndexOf('.')) + "_result.txt"));

            String word_table[] = table.split("\n");
            for (int i = 0; i < word_table.length; i++) {
                String[] variables = word_table[i].split(" \\| ");
                myWriter.printf("%-12s | %-4s | %-6s | %-8s%n", variables[0], variables[1], variables[2], variables[3]);
            }
           
            myWriter.write("\n");
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    String s = sol[i][j] + " ";
                    myWriter.write(s);
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
                e.printStackTrace();
            }



    
    }

    public static void fillPuzzle(ArrayList<String> sopa) {
        //System.out.println("Sopa de Letras: ");
        
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                puzzle[i][j] = sopa.get(i).charAt(j);
            }
        }
    }

    public static String findWord(String word) {
        int[][] direcoes = {
                { -1, 0 }, // UP
                { -1, -1 }, // UPLEFT
                { 0, -1 }, // LEFT
                { 1, -1 }, // DOWNLEFT
                { 1, 0 }, // DOWN
                { 1, 1 }, // DOWNRIGHT
                { 0, 1 }, // RIGHT
                { -1, 1 } // UPRIGHT
        };
        
        String table = "";

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                if (puzzle[row][col] == Character.toLowerCase(word.charAt(0))) {
                   

                    for (int[] dir : direcoes) {
                        int x = row;
                        int y = col;
                        //boolean found = true;
                        int i;
                        for (i = 1; i < word.length(); i++) {
                            x += dir[0];
                            y += dir[1];
                            if (x < 0 || x >= 15 || y < 0 || y >= 15 ||
                                    puzzle[x][y] != Character.toLowerCase(word.charAt(i))) {
                                //found = false;
                                break;
                            }
                        }

                    
                        if (i == word.length()) {
                            x = row;
                            y = col;
                            for (i = 0; i < word.length(); i++) {
                                foundPositions[x][y] = true;
                                x += dir[0];
                                y += dir[1];
                            }
                            
                            if(!verificarCapicua(word) && !listaWords.contains(word)){

                                System.out.printf("%-12s | %-4d | %-6s | %-8s%n",
                                    word.toLowerCase(),
                                    word.length(),
                                    (row + 1) + "," + (col + 1),
                                    getDirectionName(dir));
                                    listaWords.add(word);
                            
                                table += word.toLowerCase() + " | " + word.length() + " | " + (row + 1) + "," + (col + 1) + " | " + getDirectionName(dir) + "\n";
                            }else{
                                // se a palavra já tiver no print da sout tudo 

                                if(!listaWords.contains(word)){
                                    System.out.printf("%-12s | %-4d | %-6s | %-8s%n",
                                    word.toLowerCase(),
                                    word.length(),
                                    (row + 1) + "," + (col + 1),
                                    getDirectionName(dir));
                                    listaWords.add(word);
                            
                                    table += word.toLowerCase() + " | " + word.length() + " | " + (row + 1) + "," + (col + 1) + " | " + getDirectionName(dir) + "\n";
                                }
                              
                            }
                            
                            
                        }
                    }
                }
            }
        }

        return table;
    }

    private static String getDirectionName(int[] dir) {
        if (dir[0] == -1 && dir[1] == 0)
            return "Up";
        if (dir[0] == -1 && dir[1] == -1)
            return "UpLeft";
        if (dir[0] == 0 && dir[1] == -1)
            return "Left";
        if (dir[0] == 1 && dir[1] == -1)
            return "DownLeft";
        if (dir[0] == 1 && dir[1] == 0)
            return "Down";
        if (dir[0] == 1 && dir[1] == 1)
            return "DownRight";
        if (dir[0] == 0 && dir[1] == 1)
            return "Right";
        if (dir[0] == -1 && dir[1] == 1)
            return "UpRight";
        return "N";
    }

    public static char[][] printPuzzleSolution() {
        char[][] matriz = new char[15][15]; 
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (foundPositions[i][j]) {
                    System.out.print(Character.toUpperCase(puzzle[i][j]) + " ");
                    matriz[i][j] = Character.toUpperCase(puzzle[i][j]);
                } else {
                    System.out.print("_ ");
                    matriz[i][j] = '_';
                }
            }
            System.out.println();
        }

        return matriz;
    }

    private static boolean verificarCapicua(String word){

        
        int j = word.length();
        word = word.toLowerCase();

        for(int i = 0; i<word.length(); i++) {

            char Letra = word.charAt(i);
            
            
            if (Letra != word.charAt(j-1)) {

                return false;
            }

            j--;
        }

        return true;

    }


    public static ArrayList<String> wordContainingWord(ArrayList<String> words) {
        ArrayList<String> result = new ArrayList<>(words);

        for (int i = 0; i < words.size(); i++) {
            for (int j = 0; j < words.size(); j++) {
                if (i != j && words.get(j).contains(words.get(i))) {
                    result.remove(words.get(i));
                    break;
                }
            }
        }
        
        return result;
    }

}



