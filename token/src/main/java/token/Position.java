package token;

public record Position(int line, int column) {

    @Override
    public String toString() {
        return "token.Position{" +
                "line=" + line +
                ", column=" + column +
                '}';
    }
}
