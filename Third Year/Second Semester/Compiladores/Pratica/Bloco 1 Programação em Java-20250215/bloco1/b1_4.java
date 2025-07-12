import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;

public class b1_4 {

    public static void main(String[] args) {
        
        String filename = "numbers.txt";
        Map<String, Integer> Mapa_Associativo = new HashMap<>();
        DecimalFormat ft;

        try (Scanner Reader = new Scanner(new File(filename))) {
        
            while (Reader.hasNextLine()){

                String linha = Reader.nextLine();
                String partes[] = linha.split(" ");

                Mapa_Associativo.put(partes[2], Integer.parseInt(partes[0]));

            }
        
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found");
        }

        try (Scanner scanner = new Scanner(System.in)) {

            while (scanner.hasNextLine()) {

                String linha = scanner.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] partes = linha.split(" ");

                for (String parte : partes) {

                    Integer Num = Mapa_Associativo.get(parte);
                    if (Num != null) {
                        System.out.print(Num + " ");
                    } else {
                        System.out.println(parte);
                    }
                }
                
            }
            scanner.close();

        } catch (Exception E) {
            System.out.println("Error: Scanner");
        }   
        
    }
    
}
