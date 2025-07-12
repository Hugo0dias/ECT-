public class StringType extends Type {
    public StringType() {
        super("String");
    }

    @Override
    public String toString() {
        return "String";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }
    
    public boolean isString(String value) {
        if (value == null) {
            return false;
        }
        // Explicitly exclude percentage strings
        return !value.trim().endsWith("%");
    }
}
