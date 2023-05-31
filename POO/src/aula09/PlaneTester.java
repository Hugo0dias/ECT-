
import java.util.Scanner;

public class PlaneTester {
    public static void main(String[] args) {
        PlaneManager planeManager = new PlaneManager();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nPlane Fleet Menu:");
            System.out.println("1. Add a plane to the fleet");
            System.out.println("2. Remove a plane from the fleet");
            System.out.println("3. Search for a plane");
            System.out.println("4. Print summary of all planes in the fleet");
            System.out.println("5. Print list of all commercial planes in the fleet");
            System.out.println("6. Print list of all military planes in the fleet");
            System.out.println("7. Print the fastest plane in the fleet");
            System.out.println("0. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    addPlane(planeManager, scanner);
                    break;
                case 2:
                    removePlane(planeManager, scanner);
                    break;
                case 3:
                    searchPlane(planeManager, scanner);
                    break;
                case 4:
                    printAllPlanes(planeManager);
                    break;
                case 5:
                    printCommercialPlanes(planeManager);
                    break;
                case 6:
                    printMilitaryPlanes(planeManager);
                    break;
                case 7:
                    printFastestPlane(planeManager);
                    break;
                case 0:
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void addPlane(PlaneManager planeManager, Scanner scanner) {

        String type;
        System.out.print("Enter the plane's ID: ");
        String id = scanner.next();
        System.out.print("Enter the plane's manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter the plane's model: ");
        String model = scanner.nextLine();
        System.out.print("Enter the plane's year of manufacture: ");
        int year = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the plane's passenger count: ");
        int passengerCount = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the plane's maximum speed: ");
        int maxSpeed = Integer.parseInt(scanner.nextLine());
        do {
            System.out.print("Enter the plane's type (commercial/military): ");
            type = scanner.next();
            if (!type.equals("commercial") && !type.equals("military")) {
                System.out.println("Por favor insira um dos dois tipos");
            }
        } while (!type.equals("commercial") && !type.equals("military"));

        if (type.equals("commercial")) {
            System.out.print("insira o número de tripulantes do Avião: ");
            int tripulantes = Integer.parseInt(scanner.next().trim());
            planeManager.addPlane(
                    new CommercialPlane(id, manufacturer, model, year, passengerCount, maxSpeed, tripulantes));

        } else {
            System.out.print("insira o número de munições do Avião: ");
            int municoes = Integer.parseInt(scanner.next().trim());
            MilitaryPlane militar = new MilitaryPlane(id, manufacturer, model, year, passengerCount, maxSpeed,
                    municoes);
            planeManager.addPlane(militar);
        }

    }

    private static void removePlane(PlaneManager planeManager, Scanner scanner) {

        System.out.print("Id do Avião que quer remover da Frota : ");
        String id = scanner.next();
        planeManager.removePlane(id);

    }

    private static void searchPlane(PlaneManager planeManager, Scanner scanner) {

        System.out.print("Id do Avião que pretende encontrar da Frota : ");
        String id = scanner.next();
        planeManager.searchPlane(id);

    }

    private static void printAllPlanes(PlaneManager planeManager) {

        planeManager.printAllPlanes();

    }

    private static void printCommercialPlanes(PlaneManager planeManager) {

        planeManager.getCommercialPlanes();

    }

    private static void printMilitaryPlanes(PlaneManager planeManager) {

        planeManager.getMilitaryPlanes();

    }

    private static void printFastestPlane(PlaneManager planeManager) {

        planeManager.getFastestPlane();

    }

}

// ajeitar o numero id -- done
// ajeitar tostring
// ajeitar remove (numero id nao encontrado)
// ajeitar search (numero id nao encontrado)