package exercicio_um;

public class circulo7 extends Forma {

    double raio;

    @Override
    public String toString() {
        return "{" +
                " raio='" + getRaio() + "'" + getCor() +
                "}";
    }

    public circulo7(String cor, double raio) {
        super(cor);
        this.raio = raio;
    }

    public double getRaio() {
        return this.raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    public double Area() {
        double Area = Math.PI * Math.pow(raio, 2);
        return Area;
    }

    public double Perimetro() {
        double perimetro = 2 * Math.PI * raio;
        return perimetro;

    }

}
