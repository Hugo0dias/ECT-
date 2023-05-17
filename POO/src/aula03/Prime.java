import java.util.Scanner;

class Prime {

    public static void main(String[] args) {

        int number;

        Scanner input = new Scanner(System.in);

        do {

            System.out.print("Número (maior que 0): ");
            number = input.nextInt();

            for (int index = 1; index <= number; index++) {

                int count_divisors = 0;

                for (int i = 1; i < number; i++) {

                    if (number > 2) {

                        if ((number % i) == 0) {
                            count_divisors++;
                        }

                        System.out.println(count_divisors);

                    }
                }

                if (count_divisors == 1) {
                    System.out.println(index + " é número primo");
                } else {
                    System.out.println(index + " não é número primo");
                }
            }

        } while (number <= 0);

        input.close();
    }

}