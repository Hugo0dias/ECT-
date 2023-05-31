import java.util.Scanner;

public class stringteste {

    public static void main(String[] args) {

        String nome;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print("String : ");
            nome = input.next();
            if (!nome.equals("Hugo") && !nome.equals("Manel")) {
                System.out.println("Por favor insira um dos dois tipos");
            }
        } while (!nome.equals("Hugo") && !nome.equals("Manel"));

        System.out.println(nome);

    }

}
