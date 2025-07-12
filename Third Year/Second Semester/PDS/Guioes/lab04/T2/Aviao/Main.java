import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException {
        FlightManager fm = new FlightManager();
        if (args.length > 0) {
            sc = new Scanner(new FileReader(args[0]));
        }
        System.out.println("\nEscolha uma opção: (H para ajuda)");
        while (sc.hasNextLine()) {
            char option = sc.next().trim().charAt(0);
            String config = sc.nextLine().trim();
            String ret;
            switch (option) {
                case 'H':
                    System.out.println("Opções do menu:");
                    System.out.println("H - Ajuda");
                    System.out.println("R <filename> - adiciona um voo definido num ficheiro de texto");
                    System.out.println("P <flightCode> - exibe o mapa das reservas de um voo");
                    System.out.println("S <flightCode> <T|E> <number_seats> - acrescenta uma nova reserva a um voo");
                    System.out.println("C <flightCode>:<reservationID> - cancela uma reserva");
                    System.out.println("Q - termina o programa");
                    break;
                case 'R':
                    fm.addFlightByFilename(config);
                    break;

                case 'P':
                    fm.showFlight(config);
                    break;
                case 'S':
                    ret = fm.reserveTicketByString(config);
                    if (ret != null) {
                        System.out.println(ret);
                    }
                    break;
                case 'C':
                    fm.cancelReservationByString(config);
                    break;
                case 'Q':
                    System.out.println("Programa terminou!");
                    System.exit(0);
                default:
                    System.out.println("Comando Inválido");
                    break;
            }
            System.out.println("\nEscolha uma opção: (H para ajuda)");
        }
        sc.close();
    }
}