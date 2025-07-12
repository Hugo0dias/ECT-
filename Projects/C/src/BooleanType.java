public class BooleanType extends Type {
    public BooleanType() {
        super("Boolean");
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BooleanType that = (BooleanType) obj;
        return name.equals(that.name);
    }
    

    public boolean isBoolean(String value) {
        if (value == null) {
            return false;
        }
        String lowerCaseValue = value.toLowerCase();
        return lowerCaseValue.equals("true") || lowerCaseValue.equals("false");
    }
}
