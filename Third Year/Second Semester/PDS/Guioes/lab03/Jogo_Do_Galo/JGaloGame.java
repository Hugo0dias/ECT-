public class JGaloGame implements JGaloInterface {
    private final Tabuleiro tabuleiro; // Representa o tabuleiro do jogo
    private final Jogador jogadorX; // Jogador que usa a letra 'X'
    private final Jogador jogadorO; // Jogador que usa a letra 'O'
    private Jogador jogadorAtual; // Jogador que está a jogar atualmente
    private boolean gameFinished; // Indica se o jogo terminou

    // Construtor que inicializa o jogo com o primeiro jogador
    public JGaloGame(char firstPlayer) {
        tabuleiro = new Tabuleiro();
        jogadorX = new Jogador('X');
        jogadorO = new Jogador('O');
        jogadorAtual = new Jogador(firstPlayer);

        // Define o jogador atual com base no primeiro jogador
        if (firstPlayer == 'X') {
            jogadorAtual = jogadorX;
        } else {
            jogadorAtual = jogadorO;
        }

        gameFinished = false; // Inicialmente, o jogo não terminou
    }

    @Override
    public char currentPlayer() {
        return jogadorAtual.getLetra(); // Retorna a letra do jogador atual
    }

    @Override
    public boolean play(int row, int col) {
        // Verifica se a jogada é válida e se o jogo não terminou
        if (!tabuleiro.jogadaValida(row, col) || gameFinished == true) {
            return false;
        }

        // Realiza a jogada no tabuleiro
        tabuleiro.jogar(row, col, currentPlayer());

        // Verifica se o jogador atual ganhou o jogo
        if (tabuleiro.checkWin(currentPlayer())) {
            gameFinished = true;
        } else if (tabuleiro.isBoardFull()) { // Verifica se o tabuleiro está cheio
            gameFinished = true;
        } else {
            // Troca o jogador atual
            if (jogadorAtual == jogadorX) {
                jogadorAtual = jogadorO;
            } else {
                jogadorAtual = jogadorX;
            }
        }
        return true;
    }

    @Override
    public boolean finished() {
        return gameFinished; // Retorna se o jogo terminou
    }

    @Override
    public char result() {
        // Verifica o resultado do jogo
        if (tabuleiro.checkWin('X')) {
            return 'X';
        }
        if (tabuleiro.checkWin('O')) {
            return 'O';
        }
        if (tabuleiro.isBoardFull()) {
            return ' '; // Empate
        }
        return '\0'; // Jogo ainda não terminou
    }
}
