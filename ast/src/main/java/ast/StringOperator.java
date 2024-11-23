package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;

/**
 * Representa un string.
 */
public class StringOperator implements ValueNode {
    private final String value;

    public StringOperator(String value) {
        this.value = value;
    }

    public String getValue() {
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
        StringOperator that = (StringOperator) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
