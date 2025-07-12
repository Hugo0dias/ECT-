package Ex2;

class CakeMaster {
    private CakeBuilder cakeBuilder;
    public void setCakeBuilder(CakeBuilder builder) { 
        this.cakeBuilder = builder; 
    }

    public void createCake(String message) {
        cakeBuilder.createCake();
        cakeBuilder.addTopLayer();
        cakeBuilder.addTopping();
        cakeBuilder.addMessage(message);
    }

    public void createCake(Shape shape, int layers, String message) {
        cakeBuilder.createCake();
        cakeBuilder.setCakeShape(shape);
        for (int i = 1; i < layers; i++) {
            cakeBuilder.addCakeLayer();
            cakeBuilder.addCreamLayer();
        }
        cakeBuilder.addTopLayer();
        cakeBuilder.addTopping();
        cakeBuilder.addMessage(message);
    }
    
    public Cake getCake() { 
        return cakeBuilder.getCake(); 
    }
}