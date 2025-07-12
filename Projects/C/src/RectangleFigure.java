public class RectangleFigure extends Figure {
    
    public RectangleFigure() {
        super("Rectangle");
    }

    @Override
    public String toString() {
        return "Rectangle";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RectangleFigure that = (RectangleFigure) obj;
        return name.equals(that.name);
    }
}
