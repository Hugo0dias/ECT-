package Ex1;

import java.util.ListIterator;

public class Teste {
    
    public static void main(String[] args) {
        VectorGeneric<String> vector = new VectorGeneric<>();
        vector.addElem("A");
        vector.addElem("B");
        vector.addElem("C");

        System.out.println("Using Iterator:");
        java.util.Iterator<String> iterator = vector.Iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("\nUsing ListIterator:");
        ListIterator<String> listIterator = vector.listIterator();
        while (listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }
    }
}
