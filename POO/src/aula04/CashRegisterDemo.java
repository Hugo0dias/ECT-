import java.util.ArrayList;

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public double getTotalValue() {
        return price * quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

}

class CashRegister {

    private final Product[] Caixa = new Product[5];

    public void addProduct(Product p) {
        for (int i = 0; i < Caixa.length; i++) {
            if (Caixa[i] == null) {
                Caixa[i] = p;
                break;
            }
        }
    }

    public double TotalValue() {
        double total = 0;
        for (Product p : Caixa) {
            if (p != null) {
                total = total + p.getTotalValue();
            }
        }
        return total;
    }

    public String toString() {
        String result = String.format("%-15s %10s %10s %7s\n", "Product", "Price", "Quantity", "Total");
        for (Product product : Caixa) {
            if (product != null) {
                result += String.format("%-15s %10.2f %10d %7.2f\n", product.getName(), product.getPrice(),
                        product.getQuantity(), product.getTotalValue());
            }
        }
        result += String.format("%s %.2f\n", "Total value: ", TotalValue());
        return result;
    }

}

public class CashRegisterDemo {

    public static void main(String[] args) {

        // Cria e adiciona 5 produtos
        CashRegister cr = new CashRegister();
        cr.addProduct(new Product("Book", 9.99, 3));
        cr.addProduct(new Product("Pen", 1.99, 10));
        cr.addProduct(new Product("Headphones", 29.99, 2));
        cr.addProduct(new Product("Notebook", 19.99, 5));
        cr.addProduct(new Product("Phone case", 5.99, 1));

        // TODO: Listar o conteúdo e valor total
        System.out.println(cr.toString());

    }
}