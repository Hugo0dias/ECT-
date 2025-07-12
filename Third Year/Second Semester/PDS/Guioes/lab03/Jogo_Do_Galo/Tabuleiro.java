public class Tabuleiro {
    private char[][] tabuleiro; // Matriz que representa o tabuleiro do jogo

    // Construtor que inicializa o tabuleiro vazio
    public Tabuleiro() {
        tabuleiro = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = '\0'; // Inicializa cada posição com '\0'
            }
        }
    }

    // Verifica se a jogada é válida
    public boolean jogadaValida(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && tabuleiro[row][col] == '\0';
    }

    // Realiza a jogada no tabuleiro
    public void jogar(int row, int col, char letra) {
        tabuleiro[row][col] = letra;
    }

    // Verifica se o jogador com a letra fornecida ganhou o jogo
    public boolean checkWin(char letra) {
        // Verifica linhas, colunas e diagonais
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == letra && tabuleiro[i][1] == letra && tabuleiro[i][2] == letra) {
                return true;
            }
            if (tabuleiro[0][i] == letra && tabuleiro[1][i] == letra && tabuleiro[2][i] == letra) {
                return true;
            }
        }
        if (tabuleiro[0][0] == letra && tabuleiro[1][1] == letra && tabuleiro[2][2] == letra) {
            return true;
        }
        if (tabuleiro[0][2] == letra && tabuleiro[1][1] == letra && tabuleiro[2][0] == letra) {
            return true;
        }
        return false;
    }

    // Verifica se o tabuleiro está cheio
    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == '\0') {
                    return false;
                }
            }
        }
        return true;
    }
}
