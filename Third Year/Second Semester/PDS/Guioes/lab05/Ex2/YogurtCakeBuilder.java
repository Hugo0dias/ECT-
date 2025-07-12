package Ex2;

class YogurtCakeBuilder implements CakeBuilder {
    private Cake cake;
    public void createCake() { 
        cake = new Cake();
        cake.setCakeLayer("Yogurt"); 
    }

    public void setCakeShape(Shape shape) { 
        cake.setShape(shape); 
    }

    public void addCakeLayer() { 
        cake.setNumCakeLayers(cake.getNumCakeLayers() + 1); 
    }

    public void addCreamLayer() { 
        cake.setMidLayerCream(Cream.Vanilla); 
    }

    public void addTopLayer() { 
        cake.setTopLayerCream(Cream.Red_Berries); 
    }

    public void addTopping() { 
        cake.setTopping(Topping.Chocolate); 
    }

    public void addMessage(String m) { 
        cake.setMessage(m); 
    }

    public Cake getCake() { 
        return cake; 
    }
}