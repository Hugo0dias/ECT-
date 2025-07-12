import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        Features Comandos = new Features();
        Scanner scanner = new Scanner(System.in);
        String firstArg = "";

        while (!firstArg.equals("Q")) {
            
            System.out.print("Enter command: (H for help) ");
            firstArg = scanner.nextLine();
            String[] inputArgs = firstArg.split(" ");

            switch (inputArgs[0]) {
                case "H":
                    Comandos.MenuOptions();
                    break;

                case "R":
                    if (inputArgs.length < 2) {
                        System.out.println("Erro: Nome do ficheiro não especificado.");
                    } else {
                        Comandos.ReadFileName(inputArgs[1]);
                    }
                    break;
            
                case "P":
                    Comandos.ReservationPlan(inputArgs[1]);
                    break;
            
                case "S":
                    char Class = inputArgs[2].charAt(0);
                    int foo;
                    try { foo = Integer.parseInt(inputArgs[3]); }
                    catch (NumberFormatException e) { foo = 0; }
                    Comandos.AddNewReservation(inputArgs[1], Class, foo);
                    break;

                case "C":
                    String Re_Code = inputArgs[1];
                    Comandos.CancelReservation(Re_Code);
                    break;

                case "Q":
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Invalid command");
                    System.out.println();
                    break;
            }
        }

        scanner.close();
    }
}