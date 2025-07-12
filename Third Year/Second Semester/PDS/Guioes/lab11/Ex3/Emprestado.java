package Ex3;

public class Emprestado implements Estado {
    private Livro book;

    public Emprestado(Livro book) {
        this.book = book;
    }

    @Override
    public void registerBook() {
        System.err.println("Book can't be registered when borrowed");
    }

    @Override
    public void requireBook() {
        System.err.println("Book is already borrowed");
    }

    @Override
    public void returnBook() {
        this.book.setState(new Disponivel(book));
    }

    @Override
    public void reserveBook() {
        System.err.println("Book can't be reserved when borrowed");
    }

    @Override
    public void cancelBook() {
        System.err.println("Book can't be canceled when borrowed");
    }

}
