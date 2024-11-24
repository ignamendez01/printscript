package ast;

import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa una función.
 */
@JsonTypeName("Function")
public class Function implements ValueNode {
    @JsonProperty("name")
    private String name;
    @JsonProperty("message")
    private String message;

    public Function() {
    }

    public Function(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Function that = (Function) o;
        return Objects.equals(name, that.name) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, message);
    }
}
