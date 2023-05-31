public class MilitaryPlane extends Plane {
    private int numMissiles;

    public MilitaryPlane(String id, String manufacturer, String model, int year, int maxPassengers, double maxSpeed,
            int numMissiles) {

        super(id, manufacturer, model, year, maxPassengers, maxSpeed);
        this.numMissiles = numMissiles;

    }

    public int getNumMissiles() {
        return this.numMissiles;
    }

    public void setNumMissiles(int numMissiles) {
        this.numMissiles = numMissiles;
    }

    @Override
    public String toString() {
        return " Avião militar número : " + super.getid() + "\n  Modelo : " + super.getModel() + "\n  Ano de fabrico : "
                + super.getManufacturer() + " \n  Numero de passageiros : " + super.getMaxNumOfPassengers()
                + " \n  Velocidade Máxima : " + super.getMaxSpeed() + " \n  Número de munições : " + getNumMissiles()
                + "";
    }

}