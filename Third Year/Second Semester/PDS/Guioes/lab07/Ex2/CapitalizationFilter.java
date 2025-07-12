
public class CapitalizationFilter extends FilterDecorator{
    public CapitalizationFilter(FilterInterface filterInterface){
        super(filterInterface);
    }

    @Override
    public String next(){
        String text = super.next();

        if (text.length() < 3){
            return text.toUpperCase();
        }

        String firstLetter = String.valueOf(text.charAt(0)).toUpperCase();
        String lastLetter = String.valueOf(text.charAt(text.length()-1)).toUpperCase();

        return firstLetter + text.substring(1, text.length()).toLowerCase() + lastLetter;
    }


}
