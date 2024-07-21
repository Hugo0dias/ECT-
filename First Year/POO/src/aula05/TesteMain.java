import java.util.Scanner;

public class TesteMain {

    public static void main(String[] args) {

        int mes;
        int ano;
        int dia;
        int escolha;
        DateYMD data = new DateYMD(1, 1, 0);

        Scanner input = new Scanner(System.in);

        do {

            System.out.println();
            System.out.println("--------------------------");
            System.out.println("Menu");
            System.out.println("1 - Criar data ");
            System.out.println("2 - Mostrar data ");
            System.out.println("3 - Incrementar data ");
            System.out.println("4 - Decrementar data ");
            System.out.println("0 - Sair ");
            System.out.println("------------------------");
            System.out.print("opçao: ");

            escolha = input.nextInt();

            switch (escolha) {

                case 1:

                    do {
                        System.out.println();
                        System.out.print("dia: ");
                        dia = input.nextInt();
                        System.out.print("mes: ");
                        mes = input.nextInt();
                        System.out.print("ano: ");
                        ano = input.nextInt();
                        data = new DateYMD(dia, mes, ano);
                        if (DateYMD.valid(dia, mes, ano) == false) {
                            System.out.println();
                            System.out.println("Data Inválida !!");
                        }

                    } while (DateYMD.valid(dia, mes, ano) == false);

                    break;

                case 2:

                    System.out.println();
                    System.out.println(data);

                    break;

                case 3:

                    dia = data.getDay();
                    data.increment(dia);
                    System.out.println(data);

                    break;

                case 4:

                    dia = data.getDay();
                    data.decrement();
                    System.out.println(data);

                    break;

            }

        } while (escolha != 0);

        input.close();

    }
}
