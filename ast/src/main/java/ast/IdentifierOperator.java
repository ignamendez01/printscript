package ast;

import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa una variable.
 */
@JsonTypeName("IdentifierOperator")
public class IdentifierOperator implements ValueNode {
    @JsonProperty("identifier")
    private String identifier;

    public IdentifierOperator() {
    }

    public IdentifierOperator(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdentifierOperator that = (IdentifierOperator) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
