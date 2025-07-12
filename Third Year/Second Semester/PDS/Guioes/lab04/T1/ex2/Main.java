import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        FlightManager manager = new FlightManager(); // Cria um novo gestor de voos

        // Verifica se há argumentos e, caso haja, lê o ficheiro de texto
        if (args.length > 0) {
            try {
                sc = new Scanner(new FileReader(args[0]));
                System.out.println("A ler comandos do ficheiro: " + args[0]);
            } catch (FileNotFoundException e) {
                System.out.println("ficheiro não ficheiro: " + args[0]);
                return; // Termina o programa se o ficheiro não for encontrado
            }
        }


        // Loop para processar as linhas lidas, seja do ficheiro ou da consola
        boolean isReadingFromFile = args.length > 0;
        while (true) {
            // Lê os comandos do ficheiro ou do console
            String line = sc.hasNextLine() ? sc.nextLine().trim() : null;

            // Se não houver mais linhas no ficheiro termina leitura
            if (line == null && isReadingFromFile) {
                System.out.println("Comandos do ficheiro foram processados. Modo leitura encerrado.");
                isReadingFromFile = false;
                sc = new Scanner(System.in); // Troca para modo consola (input)
            }

            // Se linha vazia -> continua
            if (line == null || line.isEmpty()) continue;

            char option = line.charAt(0); // opção
            String config = line.length() > 1 ? line.substring(1).trim() : ""; // resto é o config

            showMenu(); // Mostra o menu
            System.out.println("\nEscolha uma opção: (H para ajuda)");

            switch (option) {
                case 'R':
                    manager.addFlight(config);
                    break;
                case 'P':
                    manager.showFlight(config);
                    break;
                case 'S':
                    String[] rConfigS = config.split(" ");
                    if (rConfigS.length == 3) {
                        String fcode = rConfigS[0];
                        manager.reserveTicket(rConfigS[1] + " " + rConfigS[2], -1, fcode);
                    } else {
                        System.out.println("Comando de reserva inválido.");
                    }
                    break;
                case 'C':
                    manager.cancelReservation(config);
                    break;
                case 'Q':
                    System.out.println("Programa terminado!");
                    System.exit(0);
                    break;
                case 'H':
                    showMenu(); // Mostrar menu de ajuda
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    showMenu();
                    break;
            }
            System.out.println("\nEscolha uma opção: (H para ajuda)");
        }
    }

    private static void showMenu() {
        System.out.println("Opções do menu:");
        System.out.println("H - Mostrar o menu de ajuda");
        System.out.println("R <filename> - Registar um novo voo");
        System.out.println("P <flight_code> - Apresentar informação sobre um voo");
        System.out.println("S <flight_code class number_seats> - Reservar lugares num voo");
        System.out.println("C <reservation_code> - Cancelar uma reserva");
        System.out.println("Q - Terminar o programa");
    }
}
