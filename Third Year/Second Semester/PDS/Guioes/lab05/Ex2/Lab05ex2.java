package Ex2;


public class Lab05ex2 {
    public static void main(String[] args) {
        CakeMaster cakeMaster = new CakeMaster();
        
        CakeBuilder chocolate = new ChocolateCakeBuilder();
        cakeMaster.setCakeBuilder(chocolate);
        cakeMaster.createCake("Congratulations");
        System.out.println("Your cake is ready: " + cakeMaster.getCake());

        CakeBuilder sponge = new SpongeCakeBuilder();
        cakeMaster.setCakeBuilder(sponge);
        cakeMaster.createCake(Shape.Square, 2, "Well done");
        System.out.println("Your cake is ready: " + cakeMaster.getCake());

        CakeBuilder yogurt = new YogurtCakeBuilder();
        cakeMaster.setCakeBuilder(yogurt);
        cakeMaster.createCake(Shape.Rectangle, 3, "The best");
        System.out.println("Your cake is ready: " + cakeMaster.getCake());
    }
}
