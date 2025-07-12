
public class FilterDecorator implements FilterInterface {
    private FilterInterface filterInterface;
    public FilterDecorator(FilterInterface filterInterface){
        this.filterInterface = filterInterface;
    }

    @Override
    public boolean hasNext(){
        return filterInterface.hasNext();
    }

    @Override
    public String next(){
        return filterInterface.next();
    }
}
