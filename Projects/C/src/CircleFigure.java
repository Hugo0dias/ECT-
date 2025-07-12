public class CircleFigure extends Figure {

    public CircleFigure() {
        super("Circle");
    }

    @Override
    public String toString() {
        return "Circle";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CircleFigure that = (CircleFigure) obj;
        return name.equals(that.name);
    }

}
