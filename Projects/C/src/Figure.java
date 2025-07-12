public abstract class Figure {
    protected final String name;

    protected Figure(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Figure type = (Figure) obj;
        return name.equals(type.name);
    }
}
