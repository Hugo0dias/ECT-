
import java.util.*;

public abstract class Magazine {

    public final void sort(ArrayList<Phone> Lista, Comparator<Phone> comparator){
        doSort(Lista, comparator);
    }

    protected abstract void doSort(ArrayList<Phone> Lista, Comparator<Phone> comparator);
    
}

