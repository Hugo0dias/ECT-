package Ex1;

import java.util.ListIterator;

public class VectorIterator<T> implements ListIterator<T> {

    private VectorGeneric<T> vc;
    private int index = 0;

    public VectorIterator(VectorGeneric<T> vc) {
        this.vc = vc;
    }

    public VectorIterator(VectorGeneric<T> vc, int index) {
        this.vc = vc;
        this.index = index;
    }

    @Override
    public boolean hasNext() {
        return index < vc.totalElem();
    }

    public T FetchNext() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException();
        }
        return vc.getElem(index++);
    }

    @Override
    public boolean hasPrevious() {
        return index > 0;
    }

    @Override
    public T previous() {
        if (!hasPrevious()) {
            throw new java.util.NoSuchElementException();
        }
        return vc.getElem(--index);
    }

    @Override
    public int nextIndex() {
        return index;
    }

    @Override
    public int previousIndex() {
        return index - 1;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Remove not supported.");
    }

    @Override
    public void set(T e) {
        throw new UnsupportedOperationException("Set not supported.");
    }

    @Override
    public void add(T e) {
        throw new UnsupportedOperationException("Add not supported.");
    }

    @Override
    public T next() {
        return FetchNext();
    }
}
