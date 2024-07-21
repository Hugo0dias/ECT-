import java.util.*;

public class contactosMain {

    public static void main(String[] args) {

        int escolha;
        Scanner input = new Scanner(System.in);

        do {

            System.out.println();
            System.out.println("--------------------------");
            System.out.println("Menu");
            System.out.println("1 - Inserir Contacto ");
            System.out.println("2 - Alterar Contacto ");
            System.out.println("3 - Apagar Contacto ");
            System.out.println("4 - Procurar Contacto ");
            System.out.println("4 - Listar Contactos ");
            System.out.println("0 - Sair ");
            System.out.println("------------------------");
            System.out.print("opçao: ");

            escolha = input.nextInt();

            switch (escolha) {

                case 1:

                    System.out.println("Inserir Contacto");
                    System.out.print("Nome : ");
                    String nome = input.next();
                    System.out.print("Insira o cc: ");
                    int cc = input.nextInt();
                    System.out.println("Insira a data de nascimento (formato: dd-mm-yyyy): ");
                    System.out.print("dia: ");
                    int dia = input.nextInt();
                    System.out.print("mes: ");
                    int mes = input.nextInt();
                    System.out.print("ano: ");
                    int ano = input.nextInt();

                    DateYMD date = new DateYMD(dia, mes, ano);

                    System.out.print("Insira o email: ");
                    String email = input.nextLine();
                    System.out.print("Insira o telefone: ");
                    int phone = input.nextInt();

                    Pessoa pessoa = new Pessoa(nome, cc, date);
                    Contactos contacto = new Contactos(pessoa, email, phone);

                    contacto.addContacto(contacto);

                    System.out.println(contacto);

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:

                    break;

            }

        } while (escolha != 0);

        input.close();

    }

}
