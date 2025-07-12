import java.io.FileNotFoundException;

public interface FlightManagerInterface {
    // Metodos a implementar
    public void addFlight(String filename) throws FileNotFoundException, FileNotFoundException;
    public void reserveTicket(String rConfig, int line, String fcode);
    public void cancelReservation(String cConfig);
    public void showFlight(String flightCode);

}
