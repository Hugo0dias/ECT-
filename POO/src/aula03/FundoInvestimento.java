import java.util.Scanner;

public class FundoInvestimento {

    public static void main(String[] args) {

        int Mont_inv;
        double JuroMensal;
        double Fundo;

        Scanner input = new Scanner(System.in);

        do {

            System.out.print("Montante Investido: ");
            Mont_inv = input.nextInt();

        } while (Mont_inv <= 0 || Mont_inv % 1000 != 0);

        do {

            System.out.print("Taxa de Juro Mensal (%): ");
            JuroMensal = input.nextInt();
            JuroMensal = JuroMensal / 100;

        } while (JuroMensal < 0 || JuroMensal > 0.05);

        Fundo = Mont_inv + (Mont_inv * JuroMensal);

        for (int i = 1; i <= 12; i++) {

            Fundo = Fundo + (Fundo * JuroMensal);

        }

        System.out.println(Fundo);
        System.out.printf("Ao fim de 12 meses terá na conta: %.3f $", Fundo);

        input.close();
    }
}
