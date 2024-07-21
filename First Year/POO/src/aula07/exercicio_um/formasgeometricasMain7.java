package exercicio_um;

public class formasgeometricasMain7 {

    public static void main(String[] args) {

        circulo7 c = new circulo7("Vermelho", 2);
        System.out.println(c.Area());
        System.out.println(c.Perimetro());
        System.out.println(c.toString());

        retangulo7 r = new retangulo7("Azul", 2, 5);
        System.out.println(r.Area());
        System.out.println(r.Perimetro());
        System.out.println(r.toString());

        triangulo7 t = new triangulo7("Amarelo", 2, 4, 3);
        System.out.println(t.Area());
        System.out.println(t.Perimetro());
        System.out.println(t.toString());

    }

}
