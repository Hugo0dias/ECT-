package Ex1.Food;

import Ex1.State;
import Ex1.Temperature;


public class PortionFactory{


    public static Portion create(String type, Temperature temp) {
        if (type.equals("Beverage") && temp == Temperature.COLD) {
            return new FruitJuice(State.Liquid, temp, "Orange");
        } else if (type.equals("Beverage") && temp == Temperature.WARM) {
            return new Milk(State.Liquid, temp);
        } else if (type.equals("Meat") && temp == Temperature.COLD) {
            return new Tuna(State.Solid, temp);
        } else if (type.equals("Meat") && temp == Temperature.WARM) {
            return new Pork(State.Solid, temp);
        }
        return null;
    }
}

