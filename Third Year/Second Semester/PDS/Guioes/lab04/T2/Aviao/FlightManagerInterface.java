import java.io.FileNotFoundException;

public interface FlightManagerInterface {
    public void addFlightByFilename(String filename) throws FileNotFoundException;
    public Flight addFlightByString(String fConfig);
    public String reserveTicketByString(String rConfig);
    public void cancelReservationByString(String cConfig);
    public void showFlight(String flightCode);
 }