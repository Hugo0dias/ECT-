public class Conserva implements Products {
   
    private final String name;
    private final double Weight;
    int identation;

    
    public Conserva(String name, double Weight) {
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
        System.out.println("Conserva '" + name + "' - Weight : " + Weight);
    }
    
}

