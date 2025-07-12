public enum TicketClass {
    Executive,
    Touristic;

    public static TicketClass getTicketClass(char sym) {
        return sym == 'E' ? Executive : Touristic;
    }
}