package Ex2;

public class Contact {
    private String name;
    private int number;

    public Contact(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }


    @Override
    public String toString() {
        return this.name + "\t" + this.number;
    }
}
