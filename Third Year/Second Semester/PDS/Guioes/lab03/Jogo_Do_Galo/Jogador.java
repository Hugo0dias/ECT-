public class Jogador {
    private char letra; // Letra que representa o jogador ('X' ou 'O')

    // Construtor que inicializa o jogador com a letra fornecida
    public Jogador(char letra) {
        this.letra = letra;
    }

    // Retorna a letra do jogador
    public char getLetra() {
        return letra;
    }
}
