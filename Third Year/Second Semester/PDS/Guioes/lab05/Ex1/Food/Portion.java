package Ex1.Food;

import Ex1.State;
import Ex1.Temperature;



public interface Portion {
    public Temperature getTemperature();
    public State getState();
}