import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileName = "text.txt";

        System.out.println("Original Text:");
        applyFilter(new TextReader(fileName));

        System.out.println("\nNormalized Text:");
        applyFilter(new NormalizationFilter(new TextReader(fileName)));

        System.out.println("\nVowel Filtered Text:");
        applyFilter(new VowelFilter(new TermFilter(new TextReader(fileName))));

        System.out.println("\nCapitalized Text:");
        applyFilter(new CapitalizationFilter(new TextReader(fileName)));

        System.out.println("\nTerm Filtered Text:");
        applyFilter(new TermFilter(new TextReader(fileName)));
    }

    private static void applyFilter(FilterInterface filter) {
        while (filter.hasNext()) {
            System.out.println(filter.next());
        }
    }
}