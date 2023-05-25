public class Professor extends Pessoa {

    String Categoria;
    int Departamento;

    public Professor(String nome, int cc, DateYMD dataNasc, String Categoria, int Departamento) {
        super(nome, cc, dataNasc);
        this.Categoria = Categoria;
        this.Departamento = Departamento;
    }

    public String getCategoria() {
        return this.Categoria;
    }

    public void setCategoria(String Categoria) {
        this.Categoria = Categoria;
    }

    public int getDepartamento() {
        return this.Departamento;
    }

    public void setDepartamento(int Departamento) {
        this.Departamento = Departamento;
    }

    @Override
    public String toString() {
        return "{" +
                " Categoria='" + getCategoria() + "'" +
                ", Departamento='" + getDepartamento() + "'" +
                "}";
    }

}
