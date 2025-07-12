package Ex2;

class SpongeCakeBuilder implements CakeBuilder {
    private Cake cake;
    public void createCake() { 
        cake = new Cake(); 
        cake.setCakeLayer("Sponge"); 
    }

    public void setCakeShape(Shape shape) { 
        cake.setShape(shape); 
    }

    public void addCakeLayer() { 
        cake.setNumCakeLayers(cake.getNumCakeLayers() + 1); 
    }

    public void addCreamLayer() { 
        cake.setMidLayerCream(Cream.Red_Berries); 
        }

        public void addTopLayer() { 
        cake.setTopLayerCream(Cream.Whipped_Cream); 
        }

        public void addTopping() { 
        cake.setTopping(Topping.Fruit); 
    }

    public void addMessage(String m) { 
        cake.setMessage(m); 
    }
    
    public Cake getCake() { 
        return cake; 
    }
}

