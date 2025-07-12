public class JGaloGame implements JGaloInterface {
    private char[][] tabuleiro = new char[3][3];
    private char jogadorAtual = 'X';
    private int jogadas = 0;

    // inicia o tabuleiro todo preenchido com espaços vazios
    public JGaloGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tabuleiro[i][j] = ' ';          
            }
        }
    }

    public char currentPlayer() {
        return jogadorAtual;
    }

    public boolean play(int linha, int coluna) {
        if (tabuleiro[linha - 1][coluna - 1] == ' ') { // se a posição está vazia
            tabuleiro[linha - 1][coluna - 1] = jogadorAtual;    // entao "posiçao" marcada com o jogador atual
            jogadas++;

            if (!finished()) { // Se o jogo ainda não terminou, troca de jogador
                if(jogadorAtual == 'X') {
                    jogadorAtual = 'O';
                } else {
                    jogadorAtual = 'X';
                }
            }

            return true;
        }

        return false; // Movimento inválido (posição já ocupada)
    }

    public boolean finished() {
        return (jogadas == 9 || result() != ' ');       // se já tudo preenchido ou se já há um vencedor
    }


    public char result() {
        // Verifica linhas e colunas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] != ' ' && tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2]) // linha
                return tabuleiro[i][0];
            if (tabuleiro[0][i] != ' ' && tabuleiro[0][i] == tabuleiro[1][i] && tabuleiro[1][i] == tabuleiro[2][i]) // coluna
                return tabuleiro[0][i];
        }
        
        // Verifica diagonais
        if (tabuleiro[0][0] != ' ' && tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2])
            return tabuleiro[0][0]; // Vitória na diagonal principal
        if (tabuleiro[0][2] != ' ' && tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0])
            return tabuleiro[0][2]; // Vitória na diagonal secundária

        return ' '; // Sem vencedor
    }
}
