import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class FlightManager implements FlightManagerInterface{

private HashMap<String, Flight> MapOfFlights;

public FlightManager() {
    MapOfFlights = new HashMap<>();
}

public void addFlightByFilename(String filename) throws FileNotFoundException{
    Scanner sc;
    try {
        sc = new Scanner(new FileReader("data/" + filename));
    } catch (FileNotFoundException e) {
        System.out.println("Ficheiro não encontrado!\n");
        return;
    }

    String header = sc.nextLine();

    // Validar header
    if(!isValidHeader(header)){
        System.out.println("Header inválido: " + header);
        sc.close();
        return;
    }

    Flight flight = addFlightByString(header.substring(1));

    
    if (flight == null) {
        sc.close();
        return;
    }

    while (sc.hasNextLine()) {
        String rConfig = sc.nextLine();
        reserveTicketByString(flight, rConfig);
    }

    sc.close();
} 

public Flight addFlightByString(String fConfig) {

    String[] info = fConfig.split(":");
    String flightCode = info[0];
        
    String[] infoTouristic = info[info.length-1].split("x");
    int colsTouristic = Integer.parseInt(infoTouristic[0]);
    int rowsTouristic = Integer.parseInt(infoTouristic[1]);
    int seatsTouristic = colsTouristic*rowsTouristic;

    Flight flight = null;

    System.out.print("Código de voo " + flightCode + ". ");
    System.out.print("Lugares disponíveis: ");

    if (info.length == 2) {
        flight = new Flight(flightCode, rowsTouristic, colsTouristic);

        System.out.print(seatsTouristic + " lugares em classe Turística.\n");
        System.out.println("Classe executiva não disponível neste voo.");

    } else {
        String[] infoExecutive = info[1].split("x");
        int colsExecutive = Integer.parseInt(infoExecutive[0]);
        int rowsExecutive = Integer.parseInt(infoExecutive[1]);
        int seatsExecutive = colsExecutive*rowsExecutive;
        flight = new Flight(flightCode, rowsTouristic, colsTouristic, rowsExecutive, colsExecutive);
        
        System.out.print(seatsExecutive + " lugares em classe Executíva; ");
        System.out.print(seatsTouristic + " lugares em classe Turística.\n");
    } 
    

    MapOfFlights.put(flightCode, flight);
    return flight;
}

public String reserveTicketByString(String rConfig) {
    String[] info = rConfig.split(" "); 

    String flightCode = info[0];
    Flight flight = getFlightByFlightCode(flightCode);

    if (flight == null) {
        System.err.println("Código de Voo inválido!");
        return null;
    }

    // Separate the flightCode from the reservation config
    rConfig = rConfig.substring(flightCode.length()+1);

    return reserveTicketByString(flight, rConfig);
}

public void cancelReservationByString(String cConfig) {
    if (!isCancellingCodeValid(cConfig)) {
        System.err.println("Código de cancelamento inválido!");
        return;
    }
    
    String[] info = cConfig.split(":"); // <flightCode>:<reservationID>

    String flightCode = info[0];
    Flight flight = MapOfFlights.get(flightCode);

    int reservationID = Integer.parseInt(info[1]);
    
    if(!flight.cancelReservation(reservationID)){
        System.out.println("Não foi possível cancelar a reserva: "+cConfig+"\n");
        return;
    }
}

public void showFlight(String flightCode) {
    Flight flight = getFlightByFlightCode(flightCode);
    
    if (flight == null) {
        System.err.println("Código de Voo inválido!");
        return;
    }

    flight.showMap();
}

// Private Methods

private String reserveTicketByString(Flight flight, String rConfig) {
    if (!isReservationValid(rConfig)) {
        System.err.println("Reserva inválida!");
        return null;
    }
    
    String[] info = rConfig.split(" "); // <T|E> <reservations>
    char classSym = info[0].charAt(0);

    TicketClass ticketC = TicketClass.getTicketClass(classSym);
    int reservations = Integer.parseInt(info[1]);

    if(!flight.reserveTicket(ticketC, reservations)) {
        System.out.println("Não foi possível obter lugares para a reserva: "+rConfig+"\n");
        return null;
    } else {
        return flight.getLastReserve();
    }
}



private boolean isValidHeader(String header) {
    String regex = "^:[A-Z]{2}\\d{4}(:\\d+x\\d+){1,2}$";
    return header.matches(regex);
}


private boolean isReservationValid(String reservation) {
    return reservation.matches("[T|E] [1-9][0-9]*");
}

private boolean isCancellingCodeValid(String cConfig) {

    String[] info = cConfig.split(":");
    if (info.length != 2) return false;

    for (int i=0; i<2;i++) {
        System.out.println(info[0]);
    }

    String flightCode = info[0];
    Flight flight = null;
    flight = MapOfFlights.get(flightCode);

    if (flight == null) return false;
    
    String reservationID = info[1];

    if (!reservationID.matches("[1-9][0-9]*")) return false;

    System.out.println("Reserva Cancelada");

    return true;
}


private Flight getFlightByFlightCode(String flightCode) {
    return MapOfFlights.get(flightCode);
}
}