package exercicio_um;

public class triangulo7 extends Forma {

    double l1;
    double l2;
    double l3;

    @Override
    public String toString() {
        return "{" +
                " l1='" + getL1() + "'" +
                ", l2='" + getL2() + "'" +
                ", l3='" + getL3() + "'" + getCor() +
                "}";
    }

    public double getL1() {
        return this.l1;
    }

    public void setL1(double l1) {
        this.l1 = l1;
    }

    public double getL2() {
        return this.l2;
    }

    public void setL2(double l2) {
        this.l2 = l2;
    }

    public double getL3() {
        return this.l3;
    }

    public void setL3(double l3) {
        this.l3 = l3;
    }

    public triangulo7(String cor, double l1, double l2, double l3) {
        super(cor);
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
    }

    public double Area() {
        double P = Perimetro() / 2;
        double Area = Math.sqrt(P * (P - l1) * (P - l2) * (P - l3));
        return Area;
    }

    public double Perimetro() {
        double perimetro = l1 + l2 + l3;
        return perimetro;

    }

}
