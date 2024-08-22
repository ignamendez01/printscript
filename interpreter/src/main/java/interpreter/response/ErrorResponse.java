package interpreter.response;

public class ErrorResponse implements InterpreterResponse{
    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
