
public class TermFilter extends FilterDecorator{

    private String[] words;
    private int wordCount;

    public TermFilter(FilterInterface filterInterface){
        super(filterInterface);
        wordCount = 0;
        words = super.next().split(" ");
    }

    @Override
    public boolean hasNext(){
        if(wordCount < words.length){
            return true;
        }
        return false;
    }

    @Override
    public String next(){
        String currentWord = words[wordCount];
        wordCount++;
        if(wordCount >= words.length && super.hasNext()){
            words = super.next().split(" ");
            wordCount = 0;
        }

        return currentWord;
    }
}
