import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    
    public static void main(String[] args) {
        
        Phone telefone1 = new Phone("INTEL", 20000, 50, "PREWRO");
        Phone telefone2 = new Phone("AMD", 1000, 5, "GERRQW");
        Phone telefone3 = new Phone("ANDROID", 5000, 1, "EWAFE");

        ArrayList<Phone> Lista = new ArrayList<>();
        Lista.add(telefone1);
        Lista.add(telefone2);
        Lista.add(telefone3);

        Sorter sorter = new Sorter(new MergeSort());
        sorter.sort(Lista, Comparator.comparing(Phone::getMemory));
        System.out.println("Ordenar por Memoria (MS)");
        Lista.forEach(System.out::println); 

        Sorter sorter1 = new Sorter(new BubbleSort());
        sorter1.sort(Lista, Comparator.comparing(Phone::getPreco));
        System.out.println("Ordenar por preco (BS)");
        Lista.forEach(System.out::println); 

        Sorter sorter2 = new Sorter(new QuickSort());
        sorter2.sort(Lista, Comparator.comparing(Phone::getCamera));
        System.out.println("Ordenar por camera (QS)");
        Lista.forEach(System.out::println); 

        Sorter sorter3 = new Sorter(new MergeSort());
        sorter3.sort(Lista, Comparator.comparing(Phone::getProcessor));
        System.out.println("Ordenar por Processor (MG)");
        Lista.forEach(System.out::println); 

    }


}
