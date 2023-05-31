import DateYMD;

package exercicio_1;

public class Bolseiro extends aluno {

    Professor orientador;
    int Montante;

    public Bolseiro(String nome, int cc, DateYMD dataNasc, Professor orientador, int Montante) {
        super(nome, cc, dataNasc, dataNasc);
        this.orientador = orientador;
        this.Montante = Montante;
    }

    @Override
    public String toString() {
        return "{" +
                " professor='" + getOrientador() + "'" +
                ", Montante='" + getMontante() + "'" +
                "}";
    }

    public Professor getOrientador() {
        return this.orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public int getMontante() {
        return this.Montante;
    }

    public void setMontante(int Montante) {
        this.Montante = Montante;
    }

}
