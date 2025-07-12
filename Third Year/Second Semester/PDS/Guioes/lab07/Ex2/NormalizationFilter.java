
public class NormalizationFilter extends FilterDecorator{
    public NormalizationFilter(FilterInterface filterInterface){
        super(filterInterface);
    }

    @Override
    public String next(){
        String normalText = "";
        while(super.hasNext()){
            normalText += super.next().replaceAll("[^\\sa-zA-Z0-9]", "");
        }

        return normalText;
    }


}
