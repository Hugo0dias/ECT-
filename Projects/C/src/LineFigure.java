public class LineFigure extends Figure {

    public LineFigure() {
        super("Line");
    }

    @Override
    public String toString() {
        return "Line";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LineFigure that = (LineFigure) obj;
        return name.equals(that.name);
    }
    
}
