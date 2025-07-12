import java.util.HashMap;

public class SeatClass {
    // Seat Class
    private String type;
    private int queues;
    private int seatsPerQueue;
    private final HashMap<Character, Integer[]> seats;

    // Construtor
    public SeatClass(TypeTicket type, int queues, int seatsPerQueue) {
        // Verifica o tipo do lugar
        if (type == null || !type.getType().equals("Executive") && !type.getType().equals("Touristic")) {
            throw new IllegalArgumentException("Tipo de lugar inválido!");
        }
        this.type = type.getType();

        // Verifica se os valores de filas e lugares são válidos
        if (queues < 0 || seatsPerQueue < 0) {
            throw new IllegalArgumentException("Número de filas ou lugares por fila inválido!");
        }
        this.queues = queues;
        this.seatsPerQueue = seatsPerQueue;

        // Inicializar o HashMap
        this.seats = new HashMap<>();

        // Criar os lugares
        for (char c = 'A'; c < 'A' + seatsPerQueue; c++) {
            seats.put(c, new Integer[queues]);
        }

        // Inicializar todos os lugares como "vazio" (0)
        for (int i = 0; i < seatsPerQueue; i++) {
            for (int j = 0; j < queues; j++) {
                seats.get((char)('A' + i))[j] = 0;
            }
        }
    }

    // Getters e Setters

    public String getType() {
        return type;
    }

    public int getQueues() {
        return queues;
    }

    public int getSeatsPerQueue() {
        return seatsPerQueue;
    }

    public HashMap<Character, Integer[]> getSeats() {
        return seats;
    }
}
