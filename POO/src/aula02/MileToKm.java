import java.util.Scanner;

class MileToKm {

    public static void main(String[] args) {

        double km;
        double miles;
        Scanner input = new Scanner(System.in);

        System.out.print("Kilometros: ");
        km = input.nextDouble();

        miles = km / 1.609;

        System.out.printf("%.0f kilometros é equivalente a %.5f milhas", km, miles);

        input.close();
    }

}

// 1