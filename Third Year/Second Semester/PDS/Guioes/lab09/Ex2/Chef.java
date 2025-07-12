package Ex2;

public class Chef implements ChefInterface{
    private ChefInterface next;
    private FoodType foodType;

    public Chef(FoodType foodType){
        this.foodType = foodType;
    }

    @Override
    public String toString() {
        return foodType.name() + "Chef";
    }

    public void setNext(ChefInterface chef) {
        this.next = chef;
    }
    
    public void execute(Food food) {
        if (food.getType() == foodType) {
            System.out.println(this + ": Starting to cook " + food + ". Out in " + food.getTime() + " minutes!");
        } else {
            System.out.println(this + ": I can't cook that.");
            if (next == null){
                System.out.println("We're sorry but that request can't be satisfied by our service!");
                return;    
            }
            next.execute(food);
        }
        
    }

}
