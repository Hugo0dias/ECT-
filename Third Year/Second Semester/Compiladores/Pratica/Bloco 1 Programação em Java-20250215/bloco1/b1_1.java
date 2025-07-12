
import java.text.DecimalFormat;
import java.util.Scanner;

public class b1_1 {
    
    public static void main(String[] args) {

        DecimalFormat ft;     
        Scanner scanner = new Scanner(System.in);

        if (scanner.hasNextDouble()) {

            double Num1 = scanner.nextDouble();
            String Op = scanner.next();

            if (scanner.hasNextDouble()) {

                double Num2 = scanner.nextDouble();
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
            }
        }
        scanner.close();
    }
}
