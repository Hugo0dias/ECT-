import java.text.DecimalFormat;
import java.util.*; 

public class b1_3 {
    
    public static void main(String[] args) {
        
        try (Scanner scanner = new Scanner(System.in)) {
            Stack<Double> pilha = new Stack<>();
            DecimalFormat ft;
            
            if(scanner.hasNext()){
                
                String linha = scanner.nextLine().trim();
                String[] partes = linha.split(" ");
                
                for (String parte : partes) {
                    try {
                        if (parte.matches("[-+]?[0-9]*\\.?[0-9]+")) {
                            pilha.push(Double.parseDouble(parte));
                        } else {
                            
                            if (pilha.size() < 2) {
                                System.err.println("Erro: Expressão inválida.");
                                return;   
                            }
                            
                            double Num1 = pilha.pop();
                            double Num2 = pilha.pop();
                            
                            switch(parte) {
                                case "+":
                                    pilha.push(Num1 + Num2);
                                    break;
                                case "-":
                                    pilha.push(Num1 - Num2);
                                    break;
                                case "*":
                                    pilha.push(Num1 * Num2);
                                    break;
                                case "/":
                                    if (Num2 == 0) {
                                        System.err.println("Erro: Divisão por zero não é permitida.");
                                    }
                                    pilha.push(Num1 / Num2);
                                    break;
                                default:
                                    System.err.println("Erro: Operacao Invalida");
                                    break;
                            }
                        }
                        
                        System.out.println("Stack: " + pilha);
                        
                    } catch (Exception e) {
                        System.err.println("Erro: Entrada inválida.");
                    }

                }
            }
            
            if (pilha.size() == 1) {
                double number = pilha.pop();
                ft = new DecimalFormat("0.0000");
                System.out.println("Resultado final: " + ft.format(number));
            } else {
                System.err.println("Erro: Expressão mal formada.");
            }
        }
        

    }

}
