import java.util.*;

class Node {
    String value;
    Node left, right;

    Node(String value) {
        this.value = value;
        this.left = this.right = null;
    }
}

public class b1_7 {

    private static Scanner scanner;

    public static Node createPrefix() {

        if (!scanner.hasNext()) return null;
        
        String token = scanner.next();
        
        if (isNumeric(token)) {
            return new Node(token);
        }
        
        Node node = new Node(token);
        node.left = createPrefix();
        node.right = createPrefix();
        return node;

    }

    public static void printInfix(Node root) {

        if (root == null) return;
        
        boolean isOperator = "+-*/".contains(root.value);
        if (isOperator) System.out.print("(");
        
        printInfix(root.left);
        System.out.print(" " + root.value + " ");
        printInfix(root.right);
        
        if (isOperator) System.out.print(")");

    }

    public static double eval(Node root) {

        if (root == null) throw new IllegalArgumentException("Expressão inválida");
        
        if (isNumeric(root.value)) return Double.parseDouble(root.value);
        
        double leftVal = eval(root.left);
        double rightVal = eval(root.right);
        
        switch (root.value) {
            case "+": 
                return leftVal + rightVal;
            case "-": 
                return leftVal - rightVal;
            case "*": 
                return leftVal * rightVal;
            case "/":
                if (rightVal == 0) throw new ArithmeticException("Erro: Divisão por zero");
                return leftVal / rightVal;
            default: throw new IllegalArgumentException("Operador inválido");

        }
    }

    private static boolean isNumeric(String str) {

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Digite a expressão em notação prefixa:");
        
        Node root = createPrefix();
        
        System.out.print("Notação infixa: ");
        printInfix(root);
        System.out.println();
        
        System.out.println("Resultado: " + eval(root));
    }
}
