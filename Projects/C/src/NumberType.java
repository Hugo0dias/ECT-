public class NumberType extends Type {
    public NumberType() {
        super("number");
    }

    @Override
    public String toString() {
        return "number";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }
    
    public boolean isNumber(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
