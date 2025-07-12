public class Reservation {

    private static int NUM = 0;

    private TypeTicket typeTicket;
    private int numPassengers;
    private String fCode;
    private String ID;
    private final String reservationNum;
    private String[] seatCode;

    public Reservation(TypeTicket typeTicket, int numPassengers, String fCode) {
        this.typeTicket = typeTicket;
        if(numPassengers < 0) {
            System.out.println("Número de passageiros inválido!");
        }

        this.numPassengers = numPassengers;
        this.fCode = fCode;
        this.reservationNum = typeTicket + String.format("%d", NUM++);
        this.ID = fCode + ":" + NUM; // ID = fCode:reservationNum

        this.seatCode = new String[numPassengers];
    }

    // Getters e Setters
    public String getID() {
        return ID;
    }

    public TypeTicket getTypeTicket() {
        return typeTicket;
    }

    public void setTypeTicket(TypeTicket typeTicket) {
        this.typeTicket = typeTicket;
    }

    public int getNumPassengers() {
        return numPassengers;
    }

    public void setNumPassengers(int numPassengers) {
        this.numPassengers = numPassengers;
    }

    public String getFCode() {
        return fCode;
    }

    public void setFCode(String fCode) {
        this.fCode = fCode;
    }

    public String getReservationNum() {
        return reservationNum;
    }

    public String[] getSeatCode() {
        return seatCode;
    }

    public int getNum() {
        return NUM;
    }
    
}
