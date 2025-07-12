
import java.util.ArrayList;
import java.util.Comparator;

public class BubbleSort extends Magazine {
    @Override
    protected void doSort(ArrayList<Phone> phones, Comparator<Phone> comparator) {
        for (int i = 0; i < phones.size(); i++) {
            for (int j = 0; j < phones.size() - i - 1; j++) {
                if (comparator.compare(phones.get(j), phones.get(j + 1)) > 0) {
                    Phone temp = phones.get(j);
                    phones.set(j, phones.get(j + 1));
                    phones.set(j + 1, temp);
                }
            }
        }
    }
}
