package token;

public class Token {
    private String type;
    private String value;
    private int line;
    private int column;

    public Token() {
    }

    public Token(String type, String value, int line, int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        if (type.equals("STRING") || type.equals("NUMBER") || type.equals("IDENTIFIER") || type.equals("BOOLEAN")) {
            return type + "(" + value + ")";
        } else {
            return type;
        }
    }
}
