package Ex1.Food;

import Ex1.State;
import Ex1.Temperature;



public abstract class Food implements Portion{
    private Temperature temp;
    private State state;

    public Food(State state,Temperature temp){
        this.temp = temp;
        this.state = state;
    }


    public Temperature getTemperature(){
        return temp;
    }
    public State getState(){
        return state;
    }

    public String toString(){
        return this.getClass().getSimpleName() + ": Temperature " + temp.name() + ", State " + state.name();
    }
    
}
