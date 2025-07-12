public interface FlightInterface {
    public boolean reserveTicket(TicketClass ticketClass, int reservations);
    public boolean cancelReservation(int RID);
    public void showMap();
    public String getLastReserve();
}