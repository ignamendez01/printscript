package interpreter.response;

public class VariableResponse implements InterpreterResponse{
    private String type;
    private String value;

    public VariableResponse(String type, String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
