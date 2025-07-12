package Ex3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        List<Livro> library = new ArrayList<Livro>();
        library.add(new Livro("Java Anti-Stress", "1111", 2000, "Omodionah"));
        library.add(new Livro("A Guerra dos Padrões", "2222", 2010, "Jorge Omel"));
        library.add(new Livro("A Procura da Luz", "3333", 2020, "Khumatkli"));

        printLibrary(library);

        Scanner sc = new Scanner(System.in);    
        Pattern pattern = Pattern.compile("(?<livro>\\d),(?<operacao>\\d)");

        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                int livroIndex = Integer.parseInt(matcher.group("livro")) - 1;
                int operation = Integer.parseInt(matcher.group("operacao"));

                if (livroIndex >= 0 && livroIndex < library.size()) {
                    Livro livro = library.get(livroIndex);
                    switch (operation) {
                        case 1:
                            livro.registerBook();
                            break;
                        case 2:
                            livro.requireBook();
                            break;
                        case 3:
                            livro.returnBook();
                            break;
                        case 4:
                            livro.reserveBook();
                            break;
                        case 5:
                            livro.cancelBook();
                            break;
                        default:
                            System.out.println("Operação inválida");
                            System.out.println();
                    }
                } else {
                    System.out.println("Livro inválido");
                    System.out.println();
                }

                printLibrary(library);
                System.out.println(">> <livro>, <operação: (1)regista; (2)requisita; (3)devolve; (4)reserva; (5)cancela");
            } else {
                System.out.println("Entrada inválida");
            }
        }
        sc.close();
    }

    public static void printLibrary(List<Livro> library) {
        int i = 1;
        for (Livro livro : library) {
            System.out.println(i + "\t" + livro.toString());
            i++;
        }
        System.out.println(">> <livro>, <operação: (1)regista; (2)requisita; (3)devolve; (4)reserva; (5)cancela");
        System.out.println();
    }
}
