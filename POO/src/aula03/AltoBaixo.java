import java.util.Scanner;

public class AltoBaixo {

    public static void main(String[] args) {

        int number = 1 + (int) (100 * Math.random());
        int guess;
        int count = 0;

        Scanner input = new Scanner(System.in);

        do {

            System.out.print("Tentativa: ");
            guess = input.nextInt();

            if (guess > number) {
                System.out.println("Tenta um número mais pequeno");
            }

            if (guess < number) {
                System.out.println("Tenta um número maior");
            }

            count++;

        } while (guess != number);

        input.close();

        System.out.println("Parabéns !! Acertaste o número em " + count + " vezes");

    }

}
