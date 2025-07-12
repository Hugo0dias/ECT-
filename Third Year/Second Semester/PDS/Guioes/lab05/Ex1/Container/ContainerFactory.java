package Ex1.Container;

import Ex1.State;
import Ex1.Temperature;
import Ex1.Food.FruitJuice;
import Ex1.Food.Milk;
import Ex1.Food.Pork;
import Ex1.Food.Portion;
import Ex1.Food.Tuna;

public class ContainerFactory {
    public static Container create(Portion portion){
        Container container = null;

        String state = portion.getState().name();
        String temperature = portion.getTemperature().name();
        
        if(state.equals("Liquid") && temperature.equals(Temperature.COLD.name())){
            container = new PlasticBottle(portion);
        }
        if(state.equals("Liquid") && temperature.equals(Temperature.WARM.name())){
            container = new TermicBottle(portion);
        }
        if(state.equals("Solid") && temperature.equals(Temperature.WARM.name())){
            container = new Tupperware(portion);
        }
        if(state.equals("Solid") && temperature.equals(Temperature.COLD.name())){
            container = new PlasticBag(portion);
        }

        return container;
    }
}
