package interpreter.response;

public record ErrorResponse(String message) implements InterpreterResponse {
}
