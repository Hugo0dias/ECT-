public class SoldState implements State {
    public void handleRequest(Produto produto) {
        System.out.println("Product sold. Nothing left to be done");
    }
    
}