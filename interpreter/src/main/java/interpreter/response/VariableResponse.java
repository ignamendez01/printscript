package interpreter.response;

public record VariableResponse(String type, String value) implements InterpreterResponse {
}
