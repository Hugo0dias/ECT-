package lab01;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class WordSearchSolver {

    private char[][] getCleanArquive(String file) throws IOException{
        boolean isArquiveValid = true;

        Path path = Paths.get(file);
        List<String> lines = Files.readAllLines(path);

        char[][] cleanFile = new char[15][15];
        

        for(int i = 0; i < 15; i++){//for pelas linhas
            for(int j = 0; j < 15; j++){
                char c = lines.get(i).charAt(j);

                if(charIsValid(c)){
                    cleanFile[i][j] = c;
                } else{
                    isArquiveValid = false;
                }
            }
        }


        if(isArquiveValid) {
            // get the words to search
        }

        return isArquiveValid ? cleanFile : null;
    }

    private boolean charIsValid(char c) {
        boolean res = (c != '#') &&
            Character.isLowerCase(c) && 
            Character.isAlphabetic(c);

        return res;
    }


}
