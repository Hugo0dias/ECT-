import startypes.StarType;
import java.awt.*;

public class Star {
    
    public int x;
    public int y;
    public StarType type;

    public Star(int x, int y, StarType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void draw(Graphics g) {
        g.setColor(type.getColor());
        g.fillOval(x, y , type.getSize(), type.getSize());
    }
}

