package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;

/**
 * Representa un número.
 */
public class NumberOperator implements ValueNode {
    private final Number value;

    public NumberOperator(Number value) {
        this.value = value;
    }

    public Number getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NumberOperator that = (NumberOperator) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
