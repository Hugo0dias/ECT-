package Ex3;

public class Disponivel implements Estado{
    private Livro book;

    public Disponivel(Livro book) {
        this.book = book;
    }

    @Override
    public void registerBook() {
        System.err.println("Book is already available");
    }

    @Override
    public void requireBook() {
        this.book.setState(new Emprestado(book));
    }

    @Override
    public void returnBook() {
        System.err.println("Cannot return a book that is not borrowed");
    }

    @Override
    public void reserveBook() {
        this.book.setState(new Reservado(book));
    }

    @Override
    public void cancelBook() {
        System.err.println("Cannot cancel a book that is not reserved");
    }
}
