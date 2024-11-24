package ast;

import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;
/**
 * Representa una operación binaria que toma dos valores y un operador (por ejemplo, +, -, *, /).
 */
@JsonTypeName("BinaryOperation")
public class BinaryOperation implements ValueNode {
    @JsonProperty("left")
    private ValueNode left;
    @JsonProperty("symbol")
    private String symbol;
    @JsonProperty("right")
    private ValueNode right;

    public BinaryOperation() {
    }

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
