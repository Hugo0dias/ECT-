public class CommercialPlane extends Plane {
    private int numOfCrewMembers;

    public CommercialPlane(String id, String manufacturer, String model, int year, int maxNumOfPassengers,
            double maxSpeed, int numOfCrewMembers) {

        super(id, manufacturer, model, year, maxNumOfPassengers, maxSpeed);
        this.numOfCrewMembers = numOfCrewMembers;

    }

    public int getNumOfCrewMembers() {
        return this.numOfCrewMembers;
    }

    public void setNumOfCrewMembers(int numOfCrewMembers) {
        this.numOfCrewMembers = numOfCrewMembers;
    }

    @Override
    public String toString() {
        return "\n Avião comercial número : " + super.getid() + "\n  Modelo : " + super.getModel()
                + "\n  Ano de fabrico : "
                + super.getManufacturer() + " \n  Numero de passageiros : " + super.getMaxNumOfPassengers()
                + " \n  Velocidade Máxima : " + super.getMaxSpeed() + " \n  Número de tripulantess : "
                + getNumOfCrewMembers()
                + " ";
    }

}
