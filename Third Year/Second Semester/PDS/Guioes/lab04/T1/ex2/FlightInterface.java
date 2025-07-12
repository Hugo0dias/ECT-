public interface FlightInterface {
    // Metodos a implementar
    public String reserveTicket(String flightcode, TypeTicket cls, int numPassengers, Plane plane);
    public boolean cancelReserve(String ID, int numCode);
    public void overView();
    public String printInfoFlight();
    public boolean hasExecutive();
}

