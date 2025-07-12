package Ex3;

import java.util.Collection;

public class AddComand<E> implements Command {

    private E Element;
    private Collection<E> Collection;

    public AddComand(Collection<E> Collection, E Element){
        this.Collection = Collection;
        this.Element = Element;
    }

    @Override
    public void Execute() {
        Collection.add(Element);
    }

    @Override
    public void undo() {
        Collection.remove(Element);
    }
    
}
