import java.time.Year;
import java.util.Scanner;

public class calendario {

    public static void main(String[] args) {

        int mes;
        int ano;
        int dia_da_semana;

        Scanner input = new Scanner(System.in);

        do {
            System.out.print("Ano : ");
            ano = input.nextInt();
        } while (ValidateAno(ano) == false);

        do {
            System.out.print("Mes : ");
            mes = input.nextInt();
        } while (ValidateMes(mes) == false);

        System.out.println("Dia da Semana em que começa o mês : ");
        System.out.println("1 - Segunda-Feira ");
        System.out.println("2 - Terça-Feira ");
        System.out.println("3 - Quarta-Feira ");
        System.out.println("4 - Quinta-Feira ");
        System.out.println("5 - Sexta-Feira ");
        System.out.println("6 - Sábado ");
        System.out.println("7 - Domingo ");
        System.out.print("Opção : ");
        dia_da_semana = input.nextInt();

        String[] months = { "", "   January", "   February", "   March", "   April", "   May", "   June", "   July",
                "   August", "   September", "   October", "   November", "   December" };
        int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        if (ano % 4 == 0 && ano % 100 != 0 && ano % 400 == 0) {
            days[2] = 29;
        } else {
            days[2] = 28;
        }

        System.out.println(" " + months[mes] + " " + ano);
        System.out.println("Su  Mo  Tu  We  Th  Fr  Sa");

        for (int i = 0; i < dia_da_semana; i++) {
            System.out.print("    ");
        }
        for (int i = 1; i <= days[mes]; i++) {
            System.out.printf("%2d  ", i);
            if ((i + dia_da_semana) % 7 == 0) {
                System.out.println();
            }

        }

        input.close();

    }

    public static boolean ValidateMes(int mes) {

        if (mes > 12 || mes < 1) {
            return false;
        }
        return true;
    }

    public static boolean ValidateAno(int ano) {

        if (ano < 1) {
            return false;
        }
        return true;
    }

    public static boolean Bissexto(int ano) {

        if (ano % 4 == 0 && ano % 100 != 0 && ano % 400 == 0) {

            return true;
        }
        return false;
    }

}
