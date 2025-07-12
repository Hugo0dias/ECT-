public class Main {
    public static void main(String[] args) {
        Gestor manager = new Gestor("João");
        Cliente client1 = new Cliente("Gui");
        Cliente client2 = new Cliente("Maria");
        Cliente client3 = new Cliente("Pedro");

        Produto prod1 = new Produto("iPhone", 1200.00);
        Produto prod2 = new Produto("Computador", 2400.00);
        Produto prod3 = new Produto("Relógio", 100.00);
        Produto prod4 = new Produto("Quadro", 420.00);
        Produto prod5 = new Produto("Móvel", 350.00);

        manager.addProduct(prod1);
        manager.addProduct(prod2);
        manager.addProduct(prod3);
        manager.addProduct(prod4);
        manager.addProduct(prod5);

        client1.bid(prod1, 1150.00);
        client2.bid(prod1, 1300.00);
        client3.bid(prod1, 1450.00);

        client1.bid(prod3, 150.00);
        client2.bid(prod5, 400.00);
        client3.bid(prod5, 360.00);

        // Simulate time passing for auctions to process
        System.out.println("Checking auction results...");
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }

        for (Produto p : manager.products) {
            p.getState().handleRequest(p);
        }
    }    
}