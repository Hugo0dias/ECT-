package Ex3;

public class Inventario implements Estado{
    private Livro book;

    public Inventario(Livro book) {
        this.book = book;
    }

    @Override
    public void registerBook() {
        book.setState(new Disponivel(book));
    }

    @Override
    public void requireBook() {
        System.err.println("Book can't be required from inventory");
    }

    @Override
    public void returnBook() {
        System.err.println("Book can't be returned from inventory");
    }

    @Override
    public void reserveBook() {
        System.err.println("Book can't be reserved from inventory");
    }

    @Override
    public void cancelBook() {
        System.err.println("Book can't be canceled from inventory");
    }
}
