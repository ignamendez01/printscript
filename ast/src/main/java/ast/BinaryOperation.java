package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;
/**
 * Representa una operación binaria que toma dos valores y un operador (por ejemplo, +, -, *, /).
 */
public class BinaryOperation implements ValueNode {
    private final ValueNode left;
    private final String symbol;
    private final ValueNode right;

    public BinaryOperation(ValueNode left, String symbol, ValueNode right) {
        this.left = left;
        this.symbol = symbol;
        this.right = right;
    }

    public ValueNode getLeft() {
        return left;
    }

    public String getSymbol() {
        return symbol;
    }

    public ValueNode getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinaryOperation that = (BinaryOperation) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(symbol, that.symbol) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, symbol, right);
    }
}
