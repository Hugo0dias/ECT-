package Ex3;

public class Reservado implements Estado{
    private Livro book;

    public Reservado(Livro book) {
        this.book = book;
    }

    @Override
    public void registerBook() {
        System.err.println("Book is already registered");
    }

    @Override
    public void requireBook() {
        System.err.println("Book is already required");
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
        book.setState(new Disponivel(book));
    }
}
