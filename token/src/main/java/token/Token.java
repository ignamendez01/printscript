package token;

public class Token {
    private final String type;
    private final String value;
    private final Position positionStart;

    public Token(String type, String value, Position position) {
        this.type = type;
        this.value = value;
        this.positionStart = position;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public Position getPosition() {
        return positionStart;
    }

    @Override
    public String toString() {
        return switch (type) {
            case "STRING", "NUMBER", "IDENTIFIER", "BOOLEAN" -> type + "(" + value + ")";
            default -> type;
        };
    }
}
