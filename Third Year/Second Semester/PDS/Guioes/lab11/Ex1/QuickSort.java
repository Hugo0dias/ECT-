

import java.util.ArrayList;
import java.util.Comparator;

public class QuickSort extends Magazine {
    @Override
    protected void doSort(ArrayList<Phone> phones, Comparator<Phone> comparator) {
        quickSort(phones, comparator, 0, phones.size() - 1);
    }

    private void quickSort(ArrayList<Phone> phones, Comparator<Phone> comparator, int low, int high) {
        if (low < high) {
            int pi = partition(phones, comparator, low, high);
            quickSort(phones, comparator, low, pi - 1);
            quickSort(phones, comparator, pi + 1, high);
        }
    }

    private int partition(ArrayList<Phone> phones, Comparator<Phone> comparator, int low, int high) {
        Phone pivot = phones.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(phones.get(j), pivot) < 0) {
                i++;
                Phone temp = phones.get(i);
                phones.set(i, phones.get(j));
                phones.set(j, temp);
            }
        }
        Phone temp = phones.get(i + 1);
        phones.set(i + 1, phones.get(high));
        phones.set(high, temp);
        return i + 1;
    }
}