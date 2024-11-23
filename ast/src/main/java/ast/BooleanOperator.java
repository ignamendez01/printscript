package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;

/**
 * Representa un elemento de tipo booleano.
 */
public class BooleanOperator implements ValueNode {
    private final boolean value;

    public BooleanOperator(boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
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
        BooleanOperator that = (BooleanOperator) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
