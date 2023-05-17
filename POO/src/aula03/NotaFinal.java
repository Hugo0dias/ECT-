import java.util.Scanner;

public class NotaFinal {

    public static void main(String[] args) {

        double NotaP;
        double NotaTP;
        int alunos;

        Scanner input = new Scanner(System.in);

        System.out.print("Número de alunos: ");
        alunos = input.nextInt();

        double[][] table = new double[alunos][2];

        for (int i = 1; i <= alunos; i++) {

            System.out.print("Nota prática: ");
            table[i][0] = input.nextDouble();

            System.out.print("Nota teórico prática: ");
            table[i][1] = input.nextDouble();

        }

        System.out.println("\nResultados\n");
        System.out.println("NotaT NotaP Pauta");

        for (int i = 1; i <= alunos; i++) {

        }

        input.close();

    }

}
