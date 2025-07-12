import java.util.ArrayList;
import java.util.List;

public class ListType extends Type {
    private final String rawListString;
    private final BooleanType booleanType = new BooleanType();
    private final NumberType numberType = new NumberType();
    private final StringType stringType = new StringType();
    private final ImageType imageType = new ImageType();
    private final PercentageType percentageType = new PercentageType();

    public ListType(String rawListString) {
        super("List");
        this.rawListString = rawListString;
    }

    public boolean hasUniformType() {
        List<String> elements = parseList(rawListString);
        if (elements.isEmpty()) return true;

        Type firstType = getElementType(elements.get(0));
        if (firstType == null) return false;

        // Special handling for nested lists
        if (firstType instanceof ListType) {
            String nestedType = ((ListType) firstType).getUniformElementType();
            if (nestedType == null) return false;
            
            for (String element : elements) {
                Type currentType = getElementType(element);
                if (!(currentType instanceof ListType) || 
                    !((ListType) currentType).getUniformElementType().equals(nestedType)) {
                    return false;
                }
            }
            return true;
        }

        // Normal type checking for primitive types
        for (String element : elements) {
            Type currentType = getElementType(element);
            if (currentType == null || !currentType.equals(firstType)) {
                return false;
            }
        }
        return true;
    }

    /**
     * For nested lists, returns the uniform type of elements if consistent
     */
    public String getUniformElementType() {
        List<String> elements = parseList(rawListString);
        if (elements.isEmpty()) return null;

        Type firstType = getElementType(elements.get(0));
        if (firstType == null) return null;

        if (firstType instanceof ListType) {
            return "List<" + ((ListType) firstType).getUniformElementType() + ">";
        }

        for (String element : elements) {
            Type currentType = getElementType(element);
            if (currentType == null || !currentType.equals(firstType)) {
                return null;
            }
        }
        return firstType.toString();
    }

    private List<String> parseList(String rawList) {
        List<String> elements = new ArrayList<>();
        if (rawList == null || rawList.length() < 2 || 
            !rawList.startsWith("[") || !rawList.endsWith("]")) {
            return elements;
        }

        String inner = rawList.substring(1, rawList.length() - 1).trim();
        if (inner.isEmpty()) return elements;

        // Advanced parsing that handles nested lists
        int depth = 0;
        StringBuilder current = new StringBuilder();
        for (char c : inner.toCharArray()) {
            if (c == '[') depth++;
            if (c == ']') depth--;
            
            if (c == ',' && depth == 0) {
                elements.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        elements.add(current.toString().trim());
        return elements;
    }

    private Type getElementType(String element) {
        if (element.startsWith("[") && element.endsWith("]")) {
            return new ListType(element);
        }
        if (percentageType.isPercentage(element)) {
            return percentageType;
        }
        if (booleanType.isBoolean(element)) {
            return booleanType;
        }
        if (numberType.isNumber(element)) {
            return numberType;
        }
        if (stringType.isString(element)) {
            return stringType;
        }
        if (imageType.isImage(element)) {
            return imageType;
        }
        return null;
    }
}