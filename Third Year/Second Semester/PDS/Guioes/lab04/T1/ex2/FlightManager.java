import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FlightManager implements FlightManagerInterface {

    private final HashMap<String, Flight> flights;
    private String fcode;
    private Plane plane;


    public FlightManager() {
        flights = new HashMap<>();
    }

    @Override
    public void addFlight(String filename) throws FileNotFoundException {
        //
        File file = new File(filename);
        if(!file.exists()) {
            System.out.println("Ficheiro não encontrado!");
            return;
        }
        // Ler o ficheiro
        try {
            try (Scanner sc = new Scanner(file)) {
                int count = 0;
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] lineS;
                    
                    if (line.contains(":")) {
                        lineS = line.split(":");
                        fcode = lineS[1];

                        System.out.println("A registar voo " + fcode + " ...");
                        if(flights.containsKey(lineS[1])) {
                            System.out.println("Voo " + lineS[1] + " encontrado!");
                            System.out.println("A verificar reservas ...");
                        } else {
                            // Verificar se o voo tem um avião associado
                            if(lineS.length > 3) {
                                String[] planeInfoE = lineS[2].split("x");
                                String[] planeInfoT = lineS[3].split("x");

                                int nRowsE = Integer.parseInt(planeInfoE[0]);
                                int nSeatsPerRowE = Integer.parseInt(planeInfoE[1]);
                                int nRowsT = Integer.parseInt(planeInfoT[0]);
                                int nSeatsPerRowT = Integer.parseInt(planeInfoT[1]);

                                plane = new Plane(nRowsE, nSeatsPerRowE, nRowsT, nSeatsPerRowT);
                            }else { // Se não tiver avião associado
                                String[] planeInfoT = lineS[2].split("x");
                                int nRows = Integer.parseInt(planeInfoT[0]);
                                int nSeatsPerRow = Integer.parseInt(planeInfoT[1]);
                                plane = new Plane(nRows, nSeatsPerRow);
                            }
                            // Registar o voo
                            Flight flight = new Flight(fcode, plane);
                            flights.put(fcode, flight);
                        }
                    } else {
                        // Reservar Lugares
                        count++;
                        reserveTicket(line, count, fcode);
                    }                    
                    
                }
            } catch (NumberFormatException e) {
                System.out.println("Formato de ficheiro inválido!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        }
    }

    @Override
    public void reserveTicket(String rConfig, int line, String fCode) {
        // Extrair ticket type e seat code
        String[] rConfigS = rConfig.split(" ");
        
        if (rConfigS.length < 2) {
            System.out.println("Formato de reserva inválido!");
        }

        TypeTicket type = TypeTicket.getTicketClass(rConfigS[0].charAt(0)); // Extrair ticket type
        String seat = rConfigS[1]; // Extrair seat code

        // Extrair seat number do code
        int seatNum = 0;
        try {
            seatNum = Integer.parseInt(seat); // número do lugar
        } catch (NumberFormatException e) {
            System.out.println("Número de lugar inválido!");
        }

        // reserva
        Flight flight = flights.get(fCode);
        
        if (flight == null) {
            System.out.println("Voo não encontrado!");
        }
        // Verificar se o voo tem um avião associado
        Plane plane = flight.getPlane();

        System.out.println(flight.reserveTicket(fCode, type, seatNum, plane));

    }

    

    @Override
    public void cancelReservation(String cConfig) {
        // Extrair flight code e sequential reservation number
        String[] cConfigS = cConfig.split(":");
        String flightCode = cConfigS[0];
        int seqNum = Integer.parseInt(cConfigS[1]);

        // Obter o voo pelo código
        Flight flight = getFlightByCode(flightCode);

        // Verificar se o voo existe
        if (flight == null) {
            System.out.println("Voo não encontrado!");
            return;
        }

        // Verificar se a reserva existe para este voo e número de sequência
        String reservationID = flightCode + ":" + seqNum;
        HashMap<String, Reservation> reservations = flight.getReservations();
        
        if (!reservations.containsKey(reservationID)) {
            System.out.println("Reserva não encontrada!");
            return;
        }

        // Obter a reserva usando o ID
        Reservation reservation = reservations.get(reservationID);

        // Tentar cancelar a reserva
        boolean result = flight.cancelReserve(reservation.getID(), seqNum);

        if (result) {
            System.out.println("Reserva cancelada com sucesso!");
        } else {
            System.out.println("Erro ao cancelar a reserva.");
        }
    }

    @Override
    public void showFlight(String flightCode) {
        // Verificar se o voo existe e se tem um avião associado
        Flight flight = getFlightByCode(flightCode);
        
        if (flight == null) {
            System.err.println("Código de Voo inválido!");
            return;
        }

        flight.overView();
    }


    private Flight getFlightByCode(String flightCode) {
        return flights.get(flightCode);
    }


}
