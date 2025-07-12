public class StockState implements State {
    @Override    
    public void handleRequest(Produto produto) {
        System.out.println("Product in stock. Moving to auction.");
        produto.setState(new AuctionState());
    }
}