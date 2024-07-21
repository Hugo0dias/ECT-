import java.util.Scanner;

class CelciusToFahrenheit {

    public static void main(String[] args) {

        double C;
        double F;
        Scanner input = new Scanner(System.in);

        System.out.print("Graus Celsius: ");
        C = input.nextDouble();

        F = (1.8 * C) + 32;

        System.out.printf("%.1f Graus Celsius é equivalente a %.1f Fahrenheit", C, F);

        input.close();
    }

}

// 2