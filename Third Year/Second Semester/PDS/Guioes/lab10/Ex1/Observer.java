public interface Observer {
    public void getType();
    public String getName();
    public void update(Produto produto, double newBid);
}