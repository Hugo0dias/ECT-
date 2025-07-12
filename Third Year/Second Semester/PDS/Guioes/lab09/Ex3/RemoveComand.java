package Ex3;
import java.util.*;

public class RemoveComand<E> implements Command{

    private E Element;
    private Collection<E> Collection;

    public RemoveComand(Collection<E> Collection, E Element){
        this.Collection = Collection;
        this.Element = Element;
    }

    @Override
    public void Execute() {
        Collection.remove(Element);
    }

    @Override
    public void undo() {
        Collection.add(Element);
    }
    
}
