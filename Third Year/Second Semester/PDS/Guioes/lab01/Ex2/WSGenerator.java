import java.io.*;
import java.util.*;

public class WSGenerator {

    private static final int SIZE = 15;
    private final char[][] grid;
    private static ArrayList<String> Words = null;
 

    public WSGenerator() {
        this.grid = new char[SIZE][SIZE];
        //fillGrid();
        
    }

    private void fillGrid() {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(grid[i][j] == '\0'){
                    grid[i][j] = (char) ('a' + random.nextInt(26));
                }
            }
        }
    }

    public void printGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

    public boolean InserirNaSopa(String word,int row,int col, int[] direcao){
        int len = word.length();
        int dr = direcao[0];
        int dc = direcao[1];

        String palavra;

        palavra = word.toLowerCase();// passar a primeira letra para minuscula 
        
        for(int i = 0; i< len;i++){
            int newRow = row + i*dr;
            int newCol = col + i*dc;

            if(newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE){
                return false;
            }

            if(grid[newRow][newCol] != '\0' && grid[newRow][newCol]!=palavra.charAt(i)){
                return false;
            }

        }

        for(int i = 0; i<len;i++){
            int newRow = row + i*dr;
            int newCol = col + i*dc;

            grid[newRow][newCol] = palavra.charAt(i);
        }

        return true;
    }



    // ACHO QUE TEMOS PRIMEIRO DE METER AS PALAVRAS LÁ E SO DPS É QUE SE METE 
    // AS LETRAS RANDOM ACHO EU
    
    public char[][] addWords(ArrayList<String> words){
        Random random = new Random();

        for(int i = 0; i< SIZE;i++){
            for(int j = 0; j<SIZE;j++){
                grid[i][j] = '\0';
            }
        }

    

        int[][] direcoes = {
            {-1, 0},  // UP
            {-1, -1}, // UPLEFT
            {0, -1},  // LEFT
            {1, -1},  // DOWNLEFT
            {1, 0},   // DOWN
            {1, 1},   // DOWNRIGHT
            {0, 1},   // RIGHT
            {-1, 1}   // UPRIGHT

        };

        for(String word: words){
            boolean naSopa = false;
            while(!naSopa){
                int row = random.nextInt(SIZE);
                int col = random.nextInt(SIZE);
                int[] dir = direcoes[random.nextInt(direcoes.length)];

                naSopa = InserirNaSopa(word,row,col,dir);
                
            }
        }


        // meter letras aleatorias nas que sobram
        fillGrid();        



        

        return grid;
    }


    public void saveGridToFile(String outputFileName){
        try(PrintWriter pw = new PrintWriter(outputFileName)){
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    pw.print(grid[i][j]);
                }
                pw.println();
            }

            for(String word: Words){
                pw.print(word);
                pw.println();
            }
        }catch(FileNotFoundException e){
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        // $ java WSGenerator -w wordlist1.txt
        if(args.length < 2 || args.length > 4){
            System.out.println("Usage: java WSGenerator -w <filename> -s <outputFilename>");
            return;
        }
   

        String filename = args[1];
        String outputFilename = null;

        if(args.length == 4 && args[2].equals("-s")){
            outputFilename = args[3];
        }


        WSReadWords reader = new WSReadWords(filename);
        Words = reader.GetWords();

        

        
        WSGenerator generator = new WSGenerator();

        generator.addWords(Words);
        generator.printGrid();

        // ver se é suposto meter sempre assim as words separados por linhas ou é suposto meter igual ao ficheiro wl01.txt?

        for(String word: Words){
            System.out.println(word);
        }

        if(outputFilename != null){
            generator.saveGridToFile(outputFilename);
        }

        

    }
}

