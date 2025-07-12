package Ex1.Food;

import Ex1.State;
import Ex1.Temperature;


public class FruitJuice extends Food{
    private String outros;
    public FruitJuice(State state,Temperature temp, String outros){
        super(state,temp);
        this.outros = outros;
    }


    @Override
    public String toString(){
        return this.getClass().getSimpleName() + ": " + outros + ", Temperature " + getTemperature().name() + ", State " + getState().name();
    }
}
