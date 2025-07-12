public class Puzzle {
    int puzzleSize = 15;
    int lines = puzzleSize;
    int cols= puzzleSize;
    private final Character[][] matrix;
    private final Character[][] result;

    public Puzzle() {
        matrix = new Character[lines][cols];
        result = new Character[lines][cols];

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = '-';
                result[i][j] = '-';
            }
        }
    }

    public Character[][] getMatrix() {
        return matrix;
    }

    public Character[][] getResult() {
        return result;
    }


    public void setMatrix(Character[][] matriz) {
        
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = matriz[i][j];
            }
        }
    }

    public void setResult(Character[][] matrizSolucao) {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrizSolucao[i][j];
            }
        }
    }

    public int getLines() {
        return lines;
    }

    public int getCols() {
        return cols;
    }

    public int getPuzzleSize() {
        return puzzleSize;
    }

    public void printMatrix() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void printResult() {
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }
    }

    // public static Puzzle generateRandomPuzzle(Puzzle puzzle) {
    //     // Preenche a matriz com letras aleatórias
    //     for (int i = 0; i < puzzle.getLines(); i++) {
    //         for (int j = 0; j < puzzle.getCols(); j++) {
    //             puzzle.getMatrix()[i][j] = (char) (Math.random() * 26 + 'a'); // gera uma letra aleatória minúscula do alfabeto
    //         }
    //     }
    //     return puzzle;
    // }


}
