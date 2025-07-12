public class AuctionState implements State {
    @Override
    public void handleRequest(Produto produto) {
        if (produto.getHighestBid() == 0.0) {
            System.out.println("No bids received. Moving to stock.");
            produto.setState(new StockState());
        } else {
            System.out.println("Product selled.");
            produto.setState(new SoldState());
        }
    }
}