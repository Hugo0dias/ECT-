import java.text.DecimalFormat;
import java.util.*;

public class b1_2 {
    
    public static void main(String[] args) {

        DecimalFormat ft;     
        Scanner scanner = new Scanner(System.in);
        Map<String, Double> Mapa = new HashMap<>();


        while (scanner.hasNextLine()) {

            String linha = scanner.nextLine().trim();
            if (linha.isEmpty()) continue;
            String[] partes = linha.split(" ");

            if ((partes.length == 3) && (partes[1].equals("="))) {

                try {

                    double valor = Evaluate(partes[2], Mapa);
                    Mapa.put(partes[0], valor);

                } catch (Exception e) {
                    System.err.println("Erro: Expressão inválida.");
                }

            } else if (partes.length == 3){ 

                try {

                double Num1 = Evaluate(partes[0], Mapa);
                String Op = partes[1];
                double Num2 = Evaluate(partes[2], Mapa);

                double result = 0.0;

                switch (Op) {
                    case "+":
                        result = Num1 + Num2;
                        break;

                    case "-":
                        result = Num1 - Num2;
                        break;

                    case "*":
                        result = Num1 * Num2;
                        break;

                    case "/":
                        if (Num2 == 0) {
                            System.err.println("Erro: Divisão por zero não é permitida.");   
                        }
                        result = Num1 / Num2;
                        break;
                
                    default:
                        System.err.println("Erro: Operacao Invalida");
                        break;
                }

                ft = new DecimalFormat("0.0000");
                System.out.println("Resultado: " + ft.format(result));
            
                } catch (Exception e) {
                    System.err.println("Erro: Entrada inválida.");
                }
            }
        }
        scanner.close();
    }

    private static double Evaluate( String Entrada, Map<String, Double> Mapa){

        if (Mapa.containsKey(Entrada)) {
            return Mapa.get(Entrada);
        } else {
            return Double.parseDouble(Entrada);
        }
    }
}
