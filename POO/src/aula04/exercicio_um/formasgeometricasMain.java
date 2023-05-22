package exercicio_um;

public class formasgeometricasMain {

    public static void main(String[] args) {

        circulo c = new circulo(2);
        System.out.println(c.Area());
        System.out.println(c.Perimetro());
        System.out.println(c.toString());

        retangulo r = new retangulo(2, 5);
        System.out.println(r.Area());
        System.out.println(r.Perimetro());
        System.out.println(r.toString());

        triangulo t = new triangulo(2, 4, 3);
        System.out.println(t.Area());
        System.out.println(t.Perimetro());
        System.out.println(t.toString());

    }

}
