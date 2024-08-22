package ast;

import ast.interfaces.Assignation;
import ast.interfaces.ValueNode;

import java.util.Objects;

// SimpleAssignation (x = 5 + 5;)
public class SimpleAssignation implements Assignation {
    private final String identifier;
    private final ValueNode value;

    public SimpleAssignation(String identifier, ValueNode value) {
        this.identifier = identifier;
        this.value = value;
    }

    public String getIdentifier() {
        return identifier;
    }

    public ValueNode getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleAssignation that = (SimpleAssignation) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, identifier);
    }
}
