
public class VowelFilter extends FilterDecorator{
    public VowelFilter(FilterInterface filterInterface){
        super(filterInterface);
    }

    @Override
    public String next() {
        if(super.hasNext()){
            return super.next().replaceAll("[aeiouAEIOU]","");
        }

        return null;
    }
}
