public class CrossFigure extends Figure {
    
    public CrossFigure() {
        super("Cross");
    }

    @Override
    public String toString() {
        return "Cross";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CrossFigure that = (CrossFigure) obj;
        return name.equals(that.name);
    }
}
