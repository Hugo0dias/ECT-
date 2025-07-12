
import java.util.Scanner;


class Node {

    String value;
    Node left, right;

    public Node(String value) {
        this.left = null;
        this.right = null;
        this.value = value;
    }

}



public class b1_7C {

    private static Scanner scanner;
    public static Node createPrefix() {
        
        if (scanner.hasNext()) {
            
            String token = scanner.next();
            if (isNumeric(token)){
                return new Node(token);
            }

            Node node = new Node(token);
            node.left = createPrefix();
            node.right = createPrefix();
            return node;
        }
        return null;

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
        
    }
}
