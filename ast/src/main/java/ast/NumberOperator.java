package ast;

import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa un número.
 */
@JsonTypeName("NumberOperator")
public class NumberOperator implements ValueNode {
    @JsonProperty("value")
    private Number value;

    public NumberOperator() {
    }

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
