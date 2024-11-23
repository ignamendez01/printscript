package ast;

import ast.interfaces.ValueNode;

import java.util.Objects;

/**
 * Representa una función.
 */
public class Function implements ValueNode {
    private final String name;
    private final String message;

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
