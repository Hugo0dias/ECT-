public class PlusFigure extends Figure {

    public PlusFigure() {
        super("Plus");
    }

    @Override
    public String toString() {
        return "Plus";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PlusFigure that = (PlusFigure) obj;
        return name.equals(that.name);
    }
    
}
