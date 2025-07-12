package Ex1.Container;

import Ex1.State;
import Ex1.Temperature;
import Ex1.Food.Portion;

public abstract class Container {

    private Portion portion;

    public Container(Portion portion){
        this.portion = portion;
    }

    public Portion gePortion(){
        return portion;
    }

    @Override
    public String toString(){
        return this.getClass().getSimpleName() + " with portion = " + portion.toString();
    }

}
