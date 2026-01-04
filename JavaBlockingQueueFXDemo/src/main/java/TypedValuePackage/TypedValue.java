package TypedValuePackage;

public class TypedValue {
    public enum Type { INTEGER, FLOAT, DOUBLE, OTHER }
    
    private Type type;
    private Object value;

    public TypedValue(Type type, Object value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() { return type; }
    public Object getValue() { return value; }

    @Override
    public String toString() {
        return type + ": " + value.toString();
    }
}
