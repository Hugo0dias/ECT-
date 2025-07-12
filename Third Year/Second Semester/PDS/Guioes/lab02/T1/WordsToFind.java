import java.util.HashSet;
import java.util.Set;

public class WordsToFind {
    private Set<String> words;

    public WordsToFind() {
        words = new HashSet<>();
    }

    public Set<String> getWords() {
        return words;
    }

    public void setWords(Set<String> words) {
        this.words = words;
    }

    public void addWord(String word) {
        words.add(word);
    }

    public void printWords() {
        for (String word : words) {
            System.out.println(word);
        }
    }

}
