import java.util.Scanner;

public class TesteMain {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        DateYMD data = new DateYMD(31, 1, 2204);

        data.valid(22, 2, 2004);

    }
}
