package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;

public class BooleanOperator implements ValueNode {
    private final String value;

    public BooleanOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanOperator that = (BooleanOperator) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
