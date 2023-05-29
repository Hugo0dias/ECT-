package exercicio_um;

public abstract class Forma {

    String cor;

    public String getCor() {
        return this.cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public abstract double Area();

    public abstract double Perimetro();

    public abstract String toString();

    public Forma(String cor) {
        this.cor = cor;
    }

}
