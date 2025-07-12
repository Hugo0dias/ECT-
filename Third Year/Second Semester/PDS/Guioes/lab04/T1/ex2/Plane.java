public class Plane {

    private SeatClass executive = null;
    private SeatClass tourist;
    private int executiveReservedSeats = 0;
    private int touristReservedSeats = 0;

    // Construtores
    public Plane(int numQueues, int seatsPerQueue) {
        this.tourist = new SeatClass(TypeTicket.Touristic, numQueues, seatsPerQueue);
    }

    public Plane(int numQueuesE, int seatsPerQueueE, int numQueuesT, int seatsPerQueueT) {
        this.executive = new SeatClass(TypeTicket.Executive, numQueuesE, seatsPerQueueE);
        this.tourist = new SeatClass(TypeTicket.Touristic, numQueuesT, seatsPerQueueT);
    }


    // Getters e Setters
    public SeatClass getExecutive() {
        return executive;
    }

    public void setExecutive(SeatClass executive) {
        this.executive = executive;
    }

    public SeatClass getTourist() {
        return tourist;
    }

    public void setTourist(SeatClass tourist) {
        this.tourist = tourist;
    }

    public void setExecutiveReservedSeats(int number) {
        this.executiveReservedSeats = number + this.executiveReservedSeats;
    }

    public void setTouristicReservedSeats(int number) {
        this.touristReservedSeats = number + this.touristReservedSeats;
    }

    public int getExecutiveReservedSeats() {
        return this.executiveReservedSeats;
    }

    public int getTouristicReservedSeats() {
        return this.touristReservedSeats;
    }

    public int getAvailableExecutiveSeats() {
        return executive.getQueues() * executive.getSeatsPerQueue() - executiveReservedSeats;
    }

    public int getAvailableTuristSeats() {
        return tourist.getQueues() * tourist.getSeatsPerQueue() - touristReservedSeats;
    }
}
