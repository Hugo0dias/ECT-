import java.util.List;
import java.util.ArrayList;

public class Cliente implements Observer {
    private String name;
    private List<Produto> products;

    public Cliente (String name) {
        this.name = name;
        products = new ArrayList<Produto>();
    }

    @Override
    public void getType() {
        System.out.println("Cliente");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void update(Produto produto, double newBid) {
        System.out.println("Cliente -> New higher bid of " + newBid + "€ on product: " + produto.getDescription());
    }

    public void bid(Produto produto, double value) {
        produto.addObserver(this);
        produto.bid(value, this);
    }
    
}