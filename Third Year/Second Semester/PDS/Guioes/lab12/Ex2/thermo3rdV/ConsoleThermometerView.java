import java.util.Observable;
import java.util.Observer;

public class ConsoleThermometerView implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Thermometer) {
            Thermometer thermometer = (Thermometer) o;
            System.out.println("Temperatura atual: " + thermometer.getTemperature() + " ºC");
        }
    }
}
