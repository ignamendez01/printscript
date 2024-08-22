package interpreter.response;

public class SuccessResponse implements InterpreterResponse{
    private final String message;

    public SuccessResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
