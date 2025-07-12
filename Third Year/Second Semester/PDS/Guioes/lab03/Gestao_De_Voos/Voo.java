



public class Voo {
    
    private String code;
    public Aviao plane;
    private int TSeats;
    private int ESeats;
    private int reservations;
    private boolean Count = false;
    private boolean Count2 = true;
    
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    

    public Voo(String code, Aviao plane){
        this.code = code;
        this.plane = plane;
        TSeats = plane.getTCap();
        ESeats = plane.getECap();
        reservations = 1;
    }

    public String getCode() {
      return this.code;
    }
    
    public void setCode(String value) {
      this.code = value;
    }

    public Aviao getPlane(){
      return plane;
    }

    public void setPlane(Aviao plane){
      this.plane = plane;
    }

    public int getTSeats(){
      return TSeats;
    }
    public void setTSeats(int TSeats){
      this.TSeats = TSeats;
    }

    public int getESeats(){
      return ESeats;
    }

    public void setESeats(int ESeats){
      this.ESeats = ESeats;
    }

    public int getReservations() {
      return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    public String getReservedSeats(String classe) {
        
        int[][] seats = classe.equals("Tourist") ? plane.getSeatsT() : plane.getSeatsE();
        StringBuilder reservedSeats = new StringBuilder();
        char rowLabel = 'A';
    
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                if (seats[i][j] != 0) {
                    reservedSeats.append((i + 1)).append(rowLabel).append(" | ");
                }
                rowLabel++;
            }
            rowLabel = 'A';
        }
    
        if (reservedSeats.length() > 0) {
            reservedSeats.setLength(reservedSeats.length() - 3); // Remove the last " | "
        }
    
        return reservedSeats.toString();
    }

    public boolean cancelingReservation(int reservationNumber) {
        boolean found = false;
    
        // Verifica na classe Tourist
        int[][] seatsT = plane.getSeatsT();
        for (int i = 0; i < seatsT.length; i++) {
            for (int j = 0; j < seatsT[i].length; j++) {
                if (seatsT[i][j] == reservationNumber) {
                    seatsT[i][j] = 0; // Libera o assento
                    found = true;
                }
            }
        }
    
        // Verifica na classe Executive
        int[][] seatsE = plane.getSeatsE();
        for (int i = 0; i < seatsE.length; i++) {
            for (int j = 0; j < seatsE[i].length; j++) {
                if (seatsE[i][j] == reservationNumber) {
                    seatsE[i][j] = 0; // Libera o assento
                    found = true;
                }
            }
        }
    
        return found; // Retorna true se encontrou a reserva e cancelou
    }
    

    public boolean makereservation(String classe, int numReservations, boolean Flag) {
        int[][] seats = null;
        if (classe.equals("Tourist")) {
            if (numReservations > (plane.getTCap() - plane.getOccupiedT())) {
                return false;
            } else {
                seats = plane.getSeatsT();
            }
        } else {
            if (numReservations > (plane.getECap() - plane.getOccupiedE())) {
                return false;
            } else {    
                seats = plane.getSeatsE();
            }
        }

        int numCol = seats[0].length; // número de lugares por fila
        int numRow = seats.length; // número de filas
        int reservate = 0;
        int emptyRow = -1;

        // encontrar uma fila totalmente vazia
        for (int i = 0; i < numRow; i++) {
            boolean empty = true;
            for (int j = 0; j < numCol; j++) {
                if (seats[i][j] != 0) { // basta um lugar estar ocupado para a fila não ser vazia
                    empty = false;
                    break;
                }
            }
            if (empty) {
                emptyRow = i;
                break;
            }
        }

        // reservar lugares
        boolean canReserve = false;

        if (emptyRow != -1) {
            if (Flag == true) {
                System.out.print(code + ":2 = ");
            }
            if (classe.contains("Tourist")) {
                int k = 4 + emptyRow;
                for (int i = emptyRow; i < numRow && !canReserve; i++) {
                    for (int j = 0; j < numCol && !canReserve; j++) {
                        if (seats[i][j] == 0) {
                            seats[i][j] = this.reservations;
                            reservate++; // 1 reserva
                            if (Flag == true){
                                char letter = ALPHABET[j];
                                System.out.print(letter + "" + k + "|");
                            }
                            if (reservate == numReservations) {
                                canReserve = true; // todos reservados

                                break;
                            }
                        }
                    }
                    k++;
                }
            } else {
                for (int i = emptyRow; i < numRow && !canReserve; i++) {
                    for (int j = 0; j < numCol && !canReserve; j++) {
                        if (seats[i][j] == 0) {
                            seats[i][j] = this.reservations;
                            reservate++; // 1 reserva
                        
                            if (Flag == true){
                                char letter = ALPHABET[j];
                                System.out.print(letter + "" + i + "|");
                            }
                            if (reservate == numReservations) {
                                canReserve = true; // todos reservados

                                break;
                            }
                        }
                    }
                }
                
            }
            if (Flag == true){
                System.out.println("");
            }
            // Se a emptyRow não tiver lugares suficientes, tentamos reservar antes dela.
            if (reservate < numReservations) {
                
                if (Flag == true && Count == true) {
                    System.out.print(code + ":2 = ");
                }
                if (classe.contains("Tourist")) {
                    int k = 4;
                    for (int i = 0; i < emptyRow && !canReserve; i++) {
                        for (int j = 0; j < numCol && !canReserve; j++) {
                            if (seats[i][j] == 0) {
                                seats[i][j] = this.reservations;
                                reservate++;

                                if (Flag == true){
                                    
                                    char letter = ALPHABET[j];
                                    System.out.print(letter + "" + k + "|");
                                }

                                if (reservate == numReservations) {
                                    canReserve = true;
                                    break;
                                }
                            }
                        }
                        k++;
                    }
                } else {
                    for (int i = emptyRow; i < numRow && !canReserve; i++) {
                        for (int j = 0; j < numCol && !canReserve; j++) {
                            if (seats[i][j] == 0) {
                                seats[i][j] = this.reservations;
                                reservate++; // 1 reserva
                            
                                if (Flag == true){
                                    char letter = ALPHABET[j];
                                    System.out.print(letter + "" + i + "|");
                                } 
                                if (reservate == numReservations) {
                                    canReserve = true; // todos reservados

                                    break;
                                }
                            }
                        }
                    }
                }
                Count = true;
                if (Flag == true){
                    System.out.println("");
                }
                
            }
        } else { // emptyRow não existe (-1)
            if (Flag == true && Count2 == false) {
                System.out.print(code + ":2 = ");
            }
            for (int i = 0; i < numRow && !canReserve; i++) {
                for (int j = 0; j < numCol && !canReserve; j++) {
                    if (seats[i][j] == 0) {
                        seats[i][j] = this.reservations;
                        reservate++;
                        if (Flag == true){
                            char letter = ALPHABET[j];
                            System.out.print(letter + "" + i + "|");
                        }
                    }
                    if (reservate == numReservations) {
                        canReserve = true;
                    }
                }
            }
            if (Flag == true){
                System.out.println("");
            }
            Count2 = false;
        }

        if (canReserve) {
            this.reservations++;
            if (classe.equals("Tourist")) {
              plane.setSeats_T(seats);
            } else {
              plane.setSeats_E(seats);
            }
            return true;
        } else {
            return false;
        }
    }
}

