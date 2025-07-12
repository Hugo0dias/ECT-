import java.util.List;
import java.util.ArrayList;

public class Produto {
    private int code;
    private static int nextCode = 1;
    private String description;
    private double price;
    private List<Observer> observers;
    private State state;
    private double highestBid = 0;
    private Observer highestBidder = null;
    private boolean isStateTransitionInProgress = false;

    public Produto(String description, double price) {
        this.code = nextCode;
        this.description = description;
        this.price = price;
        nextCode++;
        observers = new ArrayList<Observer>();
        state = new StockState();
    }

    public void bid(Double value, Observer observer) {
        if (value > highestBid && value > price) {
            highestBid = value;
            highestBidder = observer;
            notifyAllObservers(value, observer);
        } else {
            System.out.println("There's already a higher bid or the bid is lower than the price!");
        }
    }


    public void notifyAllObservers(double value, Observer bidder) {
        for (Observer observer : observers) {
            if (observer != bidder) {
                observer.update(this, value);
            }
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void setState(State state) {
        if (isStateTransitionInProgress || this.state.equals(state)) {
            return;
        }
        isStateTransitionInProgress = true;
        this.state = state;
        state.handleRequest(this);
        isStateTransitionInProgress = false;
    }

    public State getState() {
        return state;
    }

    public double getHighestBid() {
        return highestBid;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}