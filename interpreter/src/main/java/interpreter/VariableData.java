package interpreter;

/**
 * Representa las variables, su nombre, tipo y valor.
 */
public class VariableData {
    private final String type;
    private final boolean isConst;
    private String value;

    public VariableData(String type, boolean isConst, String value) {
        this.type = type;
        this.isConst = isConst;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public boolean isConst() {
        return isConst;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
