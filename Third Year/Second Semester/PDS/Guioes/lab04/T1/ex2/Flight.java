import java.util.HashMap;
import javax.print.DocFlavor;

public class Flight implements FlightInterface {

    private final String fCode;
    private Plane plane;
    private static final HashMap<String, Reservation> reservations = new HashMap<String, Reservation>();

    public Flight(String fCode, Plane plane) {
        this.fCode = fCode;
        this.plane = plane;
    }

    @Override
    public String reserveTicket(String flightcode, TypeTicket type, int numPassengers, Plane plane){
        int passageirosRestantes = numPassengers; // passageiros que ainda não têm lugar
        if (type == TypeTicket.Executive) {
            // Verifica se a classe executiva está disponível
            if (hasExecutive()){
                if (plane.getAvailableExecutiveSeats() >= numPassengers) {
                    // Cria a reserva
                    Reservation reservation = new Reservation(type, numPassengers, flightcode);
                    reservations.put(reservation.getID(), reservation);

                    //Verifica se há filas vazias
                    for (int row = 0; row < plane.getExecutive().getQueues(); row++) {
                        boolean emptyRow = true;
                        for (int seat = 0; seat < plane.getExecutive().getSeatsPerQueue(); seat++) {
                            if (plane.getExecutive().getSeats().get((char)('A' + seat))[row] != 0) {
                                emptyRow = false;
                                break;
                            }
                        }
                        // Se a fila estiver vazia, atribui os lugares
                        if (emptyRow) {
                            for (int seat = 0; seat < plane.getExecutive().getSeatsPerQueue(); seat++) {
                                if (passageirosRestantes > 0) {
                                    plane.getExecutive().getSeats().get((char)('A' + seat))[row] = reservation.getNum();
                                    reservation.getSeatCode()[numPassengers - passageirosRestantes] = (char)('A' + seat) + String.valueOf(row + 1);
                                    passageirosRestantes--;
                                }
                            }
                        }
                    }
        
                    // Verifica se há lugares vazios nas filas (não cheias)
                    for (int f = 0; f < plane.getExecutive().getSeatsPerQueue(); f++) {
                        for (int l = 0; l < plane.getExecutive().getSeats().get((char)('A' + f)).length; l++) {
                            if (plane.getExecutive().getSeats().get((char)('A'+ f))[l] == 0 && passageirosRestantes > 0) { //lugar desocupado
                                plane.getExecutive().getSeats().get((char)('A' + f))[l]  = reservation.getNum();
                                reservation.getSeatCode()[numPassengers - passageirosRestantes] = (char)('A' + f) + String.valueOf(l + 1);
                                passageirosRestantes--;
                            }
                        }
                    }
                    // Atualiza o número de lugares reservados
                    plane.setExecutiveReservedSeats(numPassengers);                
                    return("Reserva E "+numPassengers+" realizada com sucesso!");
                } else {
                    return("Não foi possivel obter lugares para a reserva: E " + numPassengers);
                }
            } else {
                return("Classe executiva não disponível neste voo");
            }
        }

        else if (type == TypeTicket.Touristic) {
            // Verifica se a classe turística está disponível
            if (plane.getAvailableTuristSeats() >= numPassengers) {

                Reservation reservation = new Reservation(type, numPassengers, flightcode);
                reservations.put(reservation.getID(), reservation);

                for (int row = 0; row < plane.getTourist().getQueues(); row++) {
                    boolean emptyRow = true;

                    // Verifica se a fila está vazia
                    for (int seat = 0; seat < plane.getTourist().getSeatsPerQueue(); seat++) {
                        if (plane.getTourist().getSeats().get((char)('A' + seat))[row] != 0) {
                            emptyRow = false;
                            break;
                        }
                    }
                    // Se a fila estiver vazia, atribui os lugares
                    if (emptyRow) {
                        for (int seat = 0; seat < plane.getTourist().getSeatsPerQueue(); seat++) {
                            if (passageirosRestantes > 0) {
                                plane.getTourist().getSeats().get((char)('A' + seat))[row] = reservation.getNum();
                                reservation.getSeatCode()[numPassengers - passageirosRestantes] = (char)('A' + seat) + String.valueOf(row + 1);
                                passageirosRestantes--;
                            }
                        }
                    }
                }
    
                //check if there are empty seats in rows (but not full)
                for (int f = 0; f < plane.getTourist().getSeatsPerQueue(); f++) {
                    for (int l = 0; l < plane.getTourist().getSeats().get((char)('A' + f)).length; l++) {
                        if (plane.getTourist().getSeats().get((char)('A' + f))[l] == 0 && passageirosRestantes > 0) { //lugar desocupado
                            plane.getTourist().getSeats().get((char)('A' + f))[l]  = reservation.getNum();
                            reservation.getSeatCode()[numPassengers - passageirosRestantes] = (char)('A' + f) + String.valueOf(l + 1);
                            passageirosRestantes--;
                        
                        }
                    }
                }
                // Atualiza o número de lugares reservados
                plane.setTouristicReservedSeats(numPassengers);                
                System.out.print(flightcode + ":" + reservation.getNum() + " = ");
                for(String seat : reservation.getSeatCode()) {
                    System.out.print(seat + " | ");
                }
                System.out.println("\n");
                return("Reserva T "+numPassengers+" realizada com sucesso!");


            } else {
                return("Não foi possivel obter lugares para a reserva: F " + numPassengers);
            }

        }
        else {
            return("Classe de bilhete inválida");
        }
    }

    @Override
    public boolean cancelReserve(String ID, int numCode) {

        Reservation reservation = reservations.get(ID);

        int removed = 0; // Número de lugares removidos

        // Verifica se a reserva é da classe turística
        for (int i = 0; i < plane.getTourist().getSeatsPerQueue(); i++) {
            for (int j = 0; j < plane.getTourist().getQueues(); j++) {
                if (plane.getTourist().getSeats().get((char)('A' + i))[j] == reservation.getNum()) {
                    plane.getTourist().getSeats().get((char)('A' + i))[j] = 0;
                    removed++;
                }
            }
            reservations.remove(ID);
        }

        // Verifica se a reserva é da classe executiva
        if (removed == 0 && hasExecutive()){
            for (int i = 0; i < plane.getExecutive().getSeatsPerQueue(); i++) {
                for (int j = 0; j < plane.getExecutive().getQueues(); j++) {
                    if (plane.getExecutive().getSeats().get((char)('A' + i))[j] == reservation.getNum()) {
                        plane.getExecutive().getSeats().get((char)('A' + i))[j] = 0;
                        removed++;
                    }
                }
            }
            reservations.remove(ID);
        }
        
        return removed > 0;
    }
    
    @Override
    public void overView() {
        // Total de colunas (seats per queue) e total de filas (queues)
        int totalCols = plane.getTourist().getQueues(); // Colunas são as "seats per queue" da classe turística
        int totalRows = plane.getTourist().getSeatsPerQueue(); // Linhas são as "queues" da classe turística

        
        if(hasExecutive()) {
            totalCols += plane.getExecutive().getQueues(); // Adiciona as colunas da classe executiva
            if(plane.getExecutive().getSeatsPerQueue() > totalRows) {
                totalRows = plane.getExecutive().getSeatsPerQueue(); // Atualiza o total de filas se a classe executiva tiver mais
            }
        }

        // Imprime o cabeçalho
        System.out.print("  ");
        for (int i = 1; i <= totalCols; i++) {
            System.out.print("%2d ".formatted(i));
        }
        System.out.println();

        // Imprime os lugares
        if (hasExecutive()) {
            for (int i = 0; i < totalRows; i++) {
                System.out.print((char)('A' + i) + " ");
                for (int j = 0; j < totalCols; j++) {
                    if (j < plane.getExecutive().getQueues()) {
                        if (i < plane.getExecutive().getSeatsPerQueue()) {
                            if (plane.getExecutive().getSeats().get((char)('A' + i))[j] == 0) {
                                System.out.print("%2d ".formatted(0));
                            } else {
                                System.out.print("%2d ".formatted(plane.getExecutive().getSeats().get((char)('A' + i))[j]));
                            }
                        } else {
                            System.out.print("%2s ".formatted(" "));
                        }
                    } else {
                        if (plane.getTourist().getSeats().get((char)('A' + i))[j - plane.getExecutive().getQueues()] == 0) {
                            System.out.print("%2d ".formatted(0));
                        } else {
                            System.out.print("%2d ".formatted(plane.getTourist().getSeats().get((char)('A' + i))[j - plane.getExecutive().getQueues()]));
                        }
                    }
                }
                System.out.println();
            }
        } else {
            // Se não houver classe executiva
            for (int i = 0; i < totalRows; i++) {
                System.out.print((char)('A' + i) + " ");
                for (int j = 0; j < totalCols; j++) {
                        if (plane.getTourist().getSeats().get((char)('A' + i))[j] == 0) {
                            System.out.print("%2d ".formatted(0));
                        } else {
                            System.out.print("%2d ".formatted(plane.getTourist().getSeats().get((char)('A' + i))[j]));
                        }
                    }
                    System.out.println();
                }
            }
        System.out.println();
        System.out.println(printInfoFlight());
    }


    @Override
    public String printInfoFlight() {
        // Retorna o código do voo e o número de lugares disponíveis
        if(hasExecutive()) {
            return "Código de voo " + fCode + " com " + (plane.getAvailableTuristSeats() + plane.getAvailableExecutiveSeats()) + " lugares disponíveis [T: " + plane.getAvailableTuristSeats() + " E: " + plane.getAvailableExecutiveSeats() + "]";
        }else {
            return "Código de voo " + fCode + " com " + plane.getAvailableTuristSeats() + " lugares disponíveis [T: " + plane.getAvailableTuristSeats() + "]";
        }
    }

    // getters e setters
    @Override       
    public boolean hasExecutive() {
        return plane.getExecutive() != null;
    }

    public String getfCode() {
        return fCode;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public HashMap<String, Reservation> getReservations() {
        return reservations;
    }

}
