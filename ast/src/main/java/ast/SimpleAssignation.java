package ast;

import ast.interfaces.Assignation;
import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa una asignación.
 */
@JsonTypeName("SimpleAssignation")
public class SimpleAssignation implements Assignation {
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("value")
    private ValueNode value;

    public SimpleAssignation() {
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimpleAssignation that = (SimpleAssignation) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, identifier);
    }
}
