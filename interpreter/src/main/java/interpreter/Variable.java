package interpreter;

public class Variable{
    private final String identifier;
    private final String type;
    private final boolean isConst;

    public Variable(String identifier, String type, boolean isConst) {
        this.identifier = identifier;
        this.type = type;
        this.isConst = isConst;
    }

    public Variable(String identifier, String type) {
        this.identifier = identifier;
        this.type = type;
        this.isConst = false;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }

    public boolean isConst() {
        return isConst;
    }
}
