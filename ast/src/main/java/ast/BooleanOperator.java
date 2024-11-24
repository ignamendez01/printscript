package ast;

import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa un elemento de tipo booleano.
 */
@JsonTypeName("BooleanOperator")
public class BooleanOperator implements ValueNode {
    @JsonProperty("value")
    private boolean value;

    public BooleanOperator() {
    }

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
