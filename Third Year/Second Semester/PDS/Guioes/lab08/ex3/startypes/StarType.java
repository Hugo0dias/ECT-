package startypes;
import java.awt.*;

public abstract class StarType {
    private int size;
    private Color color;
    protected String description;
    protected Float[] physicalProperties;

    public StarType(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    public int getSize(){
        return this.size;
    }

    public Color getColor(){
        return this.color;
    }

    public String getDescription(){
        return this.description;
    }

    public Float[] getPhysicalProperties(){
        return this.physicalProperties;
    }
}