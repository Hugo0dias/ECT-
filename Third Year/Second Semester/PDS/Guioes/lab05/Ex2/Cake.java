package Ex2;

class Cake {
    private Shape shape = Shape.Circle; 
    private String cakeLayer;
    private int numCakeLayers = 1;
    private Cream midLayerCream;
    private Cream topLayerCream;
    private Topping topping;
    private String message;

    public void setShape(Shape shape) { 
        this.shape = shape; 
    }

    public void setCakeLayer(String cakeLayer) { 
        this.cakeLayer = cakeLayer;
    }

    public void setNumCakeLayers(int numCakeLayers) { 
        this.numCakeLayers = numCakeLayers; 
    }

    public void setMidLayerCream(Cream midLayerCream) { 
        this.midLayerCream = midLayerCream; 
    }

    public void setTopLayerCream(Cream topLayerCream) { 
        this.topLayerCream = topLayerCream; 
    }

    public void setTopping(Topping topping) 
    { 
        this.topping = topping; 
    }

    public void setMessage(String message) 
    { 
        this.message = message; 
    }
    
    @Override
    public String toString() {
        return (cakeLayer != null ? cakeLayer + " cake with " : "") + numCakeLayers + " layers" +
            (midLayerCream != null ? " and " + midLayerCream + " cream" : "") +
            "topped with " + topLayerCream + " cream and " + topping +
            "Message says: \"" + message + "\".";
    }
    
    public int getNumCakeLayers() {
        return numCakeLayers;
    }
}