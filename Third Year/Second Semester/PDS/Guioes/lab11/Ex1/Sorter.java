
import java.util.ArrayList;
import java.util.Comparator;

public class Sorter {
    
    private Magazine sorter;

    public Sorter(Magazine sorter){
        this.sorter = sorter;
    }

    public void sort(ArrayList<Phone> Lista, Comparator<Phone> comparator){
        sorter.sort(Lista, comparator);
    }

}
