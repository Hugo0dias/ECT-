public class Plane {

    String id;
    String manufacturer;
    String model;
    int year;
    int maxNumOfPassengers;
    double maxSpeed;

    public Plane(String id, String manufacturer, String model, int year, int maxNumOfPassengers, double maxSpeed) {
        this.id = id;
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.maxNumOfPassengers = maxNumOfPassengers;
        this.maxSpeed = maxSpeed;

    }

    public String getid() {
        return this.id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMaxNumOfPassengers() {
        return this.maxNumOfPassengers;
    }

    public void setMaxNumOfPassengers(int maxNumOfPassengers) {
        this.maxNumOfPassengers = maxNumOfPassengers;
    }

    public double getMaxSpeed() {
        return this.maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getid() + "'" +
                ", manufacturer='" + getManufacturer() + "'" +
                ", model='" + getModel() + "'" +
                ", year='" + getYear() + "'" +
                ", maxNumOfPassengers='" + getMaxNumOfPassengers() + "'" +
                ", maxSpeed='" + getMaxSpeed() + "'" +
                "}";
    }

}
