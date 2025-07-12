public class JGaloIMP implements JGaloInterface {

    private final int size = 3;
    private final char[][] board;
    private final char DEFAULT_VALUE = ' ';
    private final char PLAYER1;
    private final char PLAYER2;
    private int numPlays = 0;
    private char winner = ' '; 

    public JGaloIMP(String player) {
        // Verifica se o jogador é o X
        if(player.equals("X")) {
            PLAYER1 = 'X';
            PLAYER2 = 'O';
        } else {
            PLAYER1 = 'O';
            PLAYER2 = 'X';
        }
        // Monta o tabuleiro de jogo
        board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                board[i][j] = DEFAULT_VALUE;
        }
    }

    public boolean canPlay(int lin, int col) {
        // Verifica se a posição está vazia
        return board[lin - 1][col - 1] == DEFAULT_VALUE;
    }

    @Override
    public char currentPlayer() {
        // Verifica qual jogador é o atual o player1 tem numero de jogadas impares e o player2 tem numero de jogadas pares
        return numPlays % 2 == 0 ? PLAYER1 : PLAYER2;
    }

    @Override
    public boolean play(int lin, int col) {
        // Verifica se a jogada é válida
        if( lin < 1 || lin > size || col < 1 || col > size ) {
            System.out.println("Invalid play");
            return false;
        }

        // Verifica se a jogada é válida
        char player = currentPlayer();
        if (canPlay(lin, col)) {
            // Realiza a jogada
            board[lin - 1][col - 1] = player;
            numPlays++;
            if (isLastPlay(lin, col, player)) {
                winner = player;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean finished() {
        if (result() == ' '){
            // verifica se o tabuleiro está cheio
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == DEFAULT_VALUE) return false;
                }
            }
            // empate
            return true;
        } else {
            // alguem ganhou
            return true;
        }
    }

    @Override
    public char result() {
        // verifica se alguem ganhou e retorna o vencedor
        if (winner != ' ') {
            return winner;
        }
        return ' ';
    }

    public boolean isLastPlay(int lin, int col, char Player) {
        int count = 0;

        // verificacao da linha
        for (int i = 0; i < size; i++) {
            if (board[lin - 1][i] == Player) count++ ;
        }

        // se a linha tiver 3 jogadas do mesmo jogador
        if (count == 3) {
            return true;
        } else {
            count = 0;
        }

        // verificacao da coluna
        for (int i = 0; i < size; i++) {
            if (board[i][col - 1] == Player) count++ ;
        }

        if (count == 3) {
            return true;
        } else {
            count = 0;
        }

        // verificacao de diagonais
        int middle = (board.length - 1) / 2;

        if (lin == 0 && col == 0 || lin == board.length && col == board.length || lin == middle && col == middle) {
            for (int i = 0; i < size; i++) {
                if (board[i][i] == Player) count++ ;
            }
        }

        if (count == 3) {
            return true;
        } else {
            count = 0;
        }

        if (lin == 0 && col == board.length || lin == board.length && col == 0 || lin == middle && col == middle) {
            for (int i = 0; i < size; i++) {
                if (board[board.length - (i + 1)][i] == Player) count++ ;
            }
        }

        if (count == 3) {
            return true;
        } else {
            count = 0;
        }

        // se nenhuma das condicoes acima for verdadeira retorna falso, pois nao houve vencedor (empate)
        return false;

    }


}