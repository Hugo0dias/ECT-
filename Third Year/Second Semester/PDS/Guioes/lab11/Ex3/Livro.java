package Ex3;

public class Livro {
    private String title;
    private String ISBN;
    private int year;
    private String author;
    private Estado state;

    public Livro(String title, String ISBN, int year, String author) {
        this.title = title;
        this.ISBN = ISBN;
        this.year = year;
        this.author = author;
        this.state = new Inventario(this);
    }

    public void setState(Estado state) {
        this.state = state;
    }

    public String getISBN() {
        return ISBN;
    }

    public void registerBook() {
        state.registerBook();
    }

    public void requireBook() {
        state.requireBook();
    }

    public void returnBook() {
        state.returnBook();
    }

    public void reserveBook() {
        state.reserveBook();
    }

    public void cancelBook() {
        state.cancelBook();
    }

    @Override
    public String toString() {
        return String.format("%-4s %-25s %-20s %-10s", ISBN, title, author, state.getClass().getSimpleName());
    }
}
