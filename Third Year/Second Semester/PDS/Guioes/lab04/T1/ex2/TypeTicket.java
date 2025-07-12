public enum TypeTicket {
    // Enumerado para os tipos de bilhetes
    Executive("Executive"), 
    Touristic("Touristic");

    private final String type;

    TypeTicket(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static TypeTicket getTicketClass(char symb) {
        return symb == 'E' ? Executive : Touristic;
    }
}