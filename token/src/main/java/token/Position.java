package token;

public record Position(int x, int y) {

    @Override
    public String toString() {
        return "token.Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
