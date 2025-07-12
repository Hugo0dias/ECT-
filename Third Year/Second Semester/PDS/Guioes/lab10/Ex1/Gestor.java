import java.util.List;
import java.util.ArrayList;

public class Gestor implements Observer{
    private String name;
    List<Produto> products;

    public Gestor(String name) {
        this.name = name;
        products = new ArrayList<Produto>();
    }

    @Override
    public void getType() {
        System.out.println("Gestor");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void update(Produto produto, double newBid) {
        System.out.println("Gestor -> New higher bid of " + newBid + "€ on product: " + produto.getDescription());
    }

    public void addProduct(Produto produto) {
        products.add(produto);
        produto.addObserver(this);
    }
}