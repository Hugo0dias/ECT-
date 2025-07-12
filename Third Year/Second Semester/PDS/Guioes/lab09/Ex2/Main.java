package Ex2;

public class Main {
    
    public static void main(String[] args) {
        ChefInterface chef1 = new Chef(FoodType.Sushi);
        ChefInterface chef2 = new Chef(FoodType.Pasta);
        ChefInterface chef3 = new Chef(FoodType.Burger);
        ChefInterface chef4 = new Chef(FoodType.Pizza);
        ChefInterface chef5 = new Chef(FoodType.Dessert);
        
        chef1.setNext(chef2);
        chef2.setNext(chef3);
        chef3.setNext(chef4);
        chef4.setNext(chef5);

        Food f1 = new Food(FoodType.Burger, "veggie burger", 19);
        Food f2 = new Food(FoodType.Pasta, "Pasta Carbonara", 14);
        Food f3 = new Food(FoodType.Sushi, "sushi nigiri and sashimi", 7);
        Food f4 = new Food(FoodType.Pizza, "PLAIN pizza, no toppings!", 14);
        Food f5 = new Food(FoodType.Salad, "salad with tuna", 7);
        Food f6 = new Food(FoodType.Dessert, "stramberry ice cream and waffles dessert", 17);

        System.out.println("Can I please get a " + f1 + "?");
        chef1.execute(f1); System.out.println();
        System.out.println("Can I please get a " + f2 + "?");
        chef1.execute(f2); System.out.println();
        System.out.println("Can I please get a " + f3 + "?");
        chef1.execute(f3); System.out.println();
        System.out.println("Can I please get a " + f4 + "?");
        chef1.execute(f4); System.out.println();
        System.out.println("Can I please get a " + f5 + "?");
        chef1.execute(f5); System.out.println();
        System.out.println("Can I please get a " + f5 + "?");
        chef1.execute(f6);
    }

}