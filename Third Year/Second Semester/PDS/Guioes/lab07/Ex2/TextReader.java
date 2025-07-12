
import java.util.Scanner;
import java.io.*;

public class TextReader implements FilterInterface{

    private String filename;
    private Scanner sc;

    public TextReader(String fileName) throws IOException{
        this.filename = fileName;
        sc = new Scanner(new File(filename));
    }

    public boolean hasNext(){
        return sc.hasNextLine();
    }

    public String next(){
        return sc.nextLine();
    }



}
