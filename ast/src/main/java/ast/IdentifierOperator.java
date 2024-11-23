package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;

/**
 * Representa una variable.
 */
public class IdentifierOperator implements ValueNode {
    private final String identifier;

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
