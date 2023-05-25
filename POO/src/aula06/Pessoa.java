public class Pessoa {

    private int cc;
    private String nome;
    private DateYMD dataNasc;

    @Override
    public String toString() {
        return "{" +
                " cc='" + getCc() + "'" +
                ", nome='" + getNome() + "'" +
                ", dataNasc='" + getDataNasc() + "'" +
                "}";
    }

    public int getCc() {
        return this.cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DateYMD getDataNasc() {
        return this.dataNasc;
    }

    public void setDataNasc(DateYMD dataNasc) {
        this.dataNasc = dataNasc;
    }

    public Pessoa(String nome, int cc, DateYMD dataNasc) {
        this.cc = cc;
        this.nome = nome;
        this.dataNasc = dataNasc;
    }

}
