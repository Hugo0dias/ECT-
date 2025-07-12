public class Doce implements Products{
    
    private final String name;
    private final double Weight;
    public static int identation;

    
    public Doce(String name, double Weight) {
        this.name = name;
        this.Weight = Weight;
    }

    @Override
    public double get_Weight() {
        return Weight;
    }

    @Override
    public void draw() {
        for (int i = 0; i < identation; i++){
            System.out.print("   ");
        }
        System.out.println("Doce '" + name + "' - Weight : " + Weight);
    }

}
