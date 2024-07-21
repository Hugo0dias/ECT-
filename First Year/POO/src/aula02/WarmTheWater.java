import java.util.Scanner;

public class WarmTheWater {

    public static void main(String[] args) {

        double M;
        double Ti;
        double Tf;
        double Q;

        Scanner input = new Scanner(System.in);

        System.out.print("Quantidade de água: ");
        M = input.nextDouble();
        System.out.print("Temperatura inicial: ");
        Ti = input.nextDouble();
        System.out.print("Temperatura final: ");
        Tf = input.nextDouble();

        Q = M * (Tf - Ti) * 4184;

        System.out.printf("Energia = %.0f Jules", Q);

        input.close();
    }

}

// 3
