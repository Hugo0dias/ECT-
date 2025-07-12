import java.io.*;
import java.util.*;
public class Features {

    private HashMap<String,Voo> mapVoos;
    private List<Integer> Dimensoes; 
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public Features(){
        mapVoos = new HashMap<>();
        Dimensoes = new ArrayList<>();
    }

    
    public void MenuOptions(){

        System.out.println("Menu options:\n" +
        "\tR <FileName>\n" +
        "\t    - Read a TextFile with Flight Info\n" +
        "\tP <Flight_Code>\n" +
        "\t    - Show Reservation Plan\n" +
        "\tS <Flight_code> <class> <number_seats>\n" +
        "\t    - Add new reservation to a Flight\n" +
        "\tC <Reservation_Code>\n" +
        "\t    - Cancel a reservation\n" +
        "\tQ\n" +
        "\t    - Terminates the program");
    
    }
    

    public void ReadFileName(String Filename) {
        boolean Flag = false;
        String caminho = "./voos/";
        caminho += Filename;
        
        File file = new File(caminho);
        int lugaresT = 0;
        int lugaresE = 0;
        try (Scanner sc = new Scanner(file)) {
            String primeiraFrase = sc.next().trim();
            //System.out.println("Primeira frase: " + primeiraFrase);

            String[] lista = primeiraFrase.split(":");

            
            String code = lista[1];
            int[][] numSeatsE = new int[0][0];
            int[][] numSeatsT;
            int LugaresPorFilaE = 0;
            int FilasE = 0;

            // Verifica se há classe executiva e turística
            if(lista.length == 4){
                String[] Exec = lista[2].split("x");
                FilasE = Integer.parseInt(Exec[0]);  // Número de filas na classe executiva
                LugaresPorFilaE = Integer.parseInt(Exec[1]); // Lugares por fila na classe executiva
                numSeatsE = new int[FilasE][LugaresPorFilaE]; // Criação da matriz de lugares
            }

            String[] Tur = lista[lista.length - 1].split("x");
            int FilasT = Integer.parseInt(Tur[0]);
            int LugaresPorFilaT = Integer.parseInt(Tur[1]);
            numSeatsT = new int[FilasT][LugaresPorFilaT];

            if (LugaresPorFilaT > LugaresPorFilaE) {
                Dimensoes.add(LugaresPorFilaT);
            } else {
                Dimensoes.add(LugaresPorFilaE);
            }

            int FilasTotal = FilasE + FilasT;
            Dimensoes.add(FilasTotal);
            

            Aviao aviao = new Aviao(numSeatsE,numSeatsT);
            Voo voo = new Voo(code, aviao);
            mapVoos.put(code,voo);

            if(aviao.getECap() != 0){
                System.out.printf("Código de voo %s. Lugares disponíveis: %d lugares em classe Executiva; %d lugares em classe Turística. \n", code,aviao.getECap() ,aviao.getTCap());
            }else{
                System.out.printf("Código de voo %s. Lugares disponíveis: %d lugares em classe Turística. \n", code ,aviao.getTCap());
                System.out.println("Classe executiva não disponível neste voo.");
            }

            while (sc.hasNextLine()) {
                String reservaLinha = sc.nextLine().trim();
                if (reservaLinha.isEmpty()) continue;
            
                String[] resL = reservaLinha.split(" ");
                if (resL.length < 2) { 
                    System.out.println("Erro: linha de reserva mal formatada -> " + reservaLinha);
                    continue;
                }
            
                String classe = resL[0];
                int lugares;
                String classeVoo;
            
                lugares = Integer.parseInt(resL[1]); 

                if(classe.equals("T")){
                    classeVoo = "Tourist";
                }else{
                    classeVoo = "Executive";
                }
                //System.out.println(classeVoo);

                // System.out.println(lugares);
            
                if(!voo.makereservation(classeVoo, lugares, Flag)){
                    
                    System.out.printf("Não foi possível obter lugares para a reserva: %s %d \n",classe,lugares);
                }
            }


        } catch (FileNotFoundException e) {
            System.out.println("Erro ao ler o ficheiro: " + e.getMessage());
        }
    }



    // Método que imprime o plano de reservas de um voo
    public void ReservationPlan(String Code){

        int total_col = 0;
        int total_rows = 0;
        if (mapVoos.containsKey(Code)) {
            Voo flight = mapVoos.get(Code);
            Aviao plane = flight.getPlane();
            int[][] seats_E = plane.getSeatsE();
            int[][] seats_T = plane.getSeatsT();
            total_rows = seats_T[0].length;

            if (seats_E.length != 0) {
                if (seats_E[0].length > seats_T[0].length) {
                    total_rows = seats_E[0].length;
                }
                total_col = seats_E.length + seats_T.length;
                System.out.print("\t" + "  ");
                for (int i = 0; i < total_col; i++) {
                    int num = i + 1;
                    System.out.print(num + " ");
                }
                System.out.println();

                // Impressão da matriz de lugares
                for (int i = 0; i < total_rows; i++) {
                    System.out.print("\t" + ALPHABET[i] + " ");
                    for (int j = 0; j < total_col; j++) {
                        if (j < seats_E.length) {
                            if (i < seats_E[0].length) {
                                System.out.printf(seats_E[j][i] + " ");
                            } else {
                                System.out.print("  ");
                            }
                        } else {
                            if (j > 9){ 
                                System.out.print(" " + seats_T[j - seats_E.length][i] + " ");
                            } else {
                                System.out.print(seats_T[j - seats_E.length][i] + " ");
                            }
                        }
                    }
                    System.out.println();
                }
            } else {
                total_col = seats_E.length + seats_T.length;
                System.out.print("\t" + "  ");
                for (int i = 0; i < total_col; i++) {
                    int num = i + 1;
                    System.out.print(num + " ");
                }
                System.out.println();
                for (int i = 0; i < total_rows; i++) {
                    System.out.print("\t" + ALPHABET[i] + " ");
                    for (int j = 0; j < total_col; j++) {

                        System.out.print(seats_T[j][i] + " ");

                    }
                    System.out.println();
                }
            }

        } else {
            System.err.println("ERROR! Impossible to show the info for this plane once it does not exist!");
            System.exit(1);
        }
        
    

    }

    public void AddNewReservation(String Code, char Class, int Number_Seats) {
        String classe;
        boolean Flag = true;
        if (Class == 'T') {
            classe = "Tourist";
        } else if (Class == 'E') {
            classe = "Executive";
        } else {
            return;
        }

        Voo voo = mapVoos.get(Code);
       
        if (!voo.makereservation(classe, Number_Seats, Flag)) {
            System.out.printf("Não foi possível obter lugares para a reserva: %s %d\n", classe, Number_Seats);
        }
    }

    public void CancelReservation(String input) {
        String[] l = input.split(":");
        String flight_code = l[0];
        int sequential_reservation_number = Integer.parseInt(l[1].trim());

        System.out.println(flight_code);
        System.out.println(sequential_reservation_number);

        Voo voo = mapVoos.get(flight_code);
        if(voo != null){
            if(voo.cancelingReservation(sequential_reservation_number)){
                System.out.println("Reserva cancelada");
            }else{
                System.out.println("número da reserva não encontrado");
            }
        }else{
            System.out.println("o código de voo não foi encontrado");
        }


    }
    
    


}
