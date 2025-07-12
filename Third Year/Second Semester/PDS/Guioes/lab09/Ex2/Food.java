package Ex2;

public class Food {
    private FoodType type;
    private String name;
    private int time;

    public Food(FoodType type, String name, int time){
        this.name = name;
        this.type = type;
        this.time = time;
    }

    @Override
    public String toString() {
        return name;
    }

    public FoodType getType(){
        return this.type;
    }

    public int getTime(){
        return this.time;
    }

    public String getName(){
        return this.name;
    }
}