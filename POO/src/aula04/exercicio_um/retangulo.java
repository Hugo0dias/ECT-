package exercicio_um;

public class retangulo {

    double comprimento;
    double altura;

    public retangulo(double altura, double comprimento) {
        this.altura = altura;
        this.comprimento = comprimento;
    }

    @Override
    public String toString() {
        return "{" +
                " comprimento='" + getComprimento() + "'" +
                ", altura='" + getAltura() + "'" +
                "}";
    }

    public double getComprimento() {
        return this.comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public double getAltura() {
        return this.altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double Area() {
        double Area = comprimento * altura;
        return Area;
    }

    public double Perimetro() {
        double perimetro = altura * 2 + comprimento * 2;
        
        return perimetro;

    }

}
