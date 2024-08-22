package ast;

import ast.interfaces.ASTNode;

import java.util.Objects;

// UnhandledNode
public class UnhandledNode implements ASTNode {
    private final boolean value;

    public UnhandledNode(boolean value) {
        this.value = value;
    }

    public boolean isValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnhandledNode that = (UnhandledNode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
