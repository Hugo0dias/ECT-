public class PercentageType extends Type {
    public PercentageType() {
        super("Percentage");
    }

    @Override
    public String toString() {
        return "Percentage";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return true;
    }
    
    public boolean isPercentage(String value) {
        if (value == null || !value.endsWith("%")) {
            return false;
        }

        try {
            // Remove the '%' and parse as a number
            String numberPart = value.substring(0, value.length() - 1).trim();
            Double.parseDouble(numberPart); // Check if it's a valid number
            return true;
        } catch (NumberFormatException e) {
            return false; // Not a valid number before '%'
        }
    }
}
