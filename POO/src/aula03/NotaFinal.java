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

        for (int i = 0; i < alunos; i++) {

            do {
                System.out.print("Nota prática: ");
                table[i][0] = input.nextDouble();

                System.out.print("Nota teórico prática: ");
                table[i][1] = input.nextDouble();
            } while ((table[i][0] < 0 && table[i][0] > 20) || (table[i][1] < 0 && table[i][1] > 20)); // nao funiona
        }

        System.out.println("\nResultados\n");
        System.out.println("NotaT NotaP Pauta");

        for (int i = 0; i < alunos; i++) {

            NotaP = table[i][0];
            NotaTP = table[i][1];
            double NotaFinal = calcularNotaFinal(NotaTP, NotaP);
            int pauta = (int) Math.round(NotaFinal);
            System.out.println(NotaTP + "  " + NotaP + "  " + pauta);
        }

        input.close();

    }

    public static double calcularNotaFinal(double notaTP, double notaP) {

        double NotaFinal = notaTP * 0.4 + notaP * 0.6;

        return NotaFinal;

    }

}
