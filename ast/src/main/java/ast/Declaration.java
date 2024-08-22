package ast;

import ast.interfaces.ASTNode;

import java.util.Objects;

// Declaration (let x: number;)
public class Declaration implements ASTNode {
    private final String identifier;
    private final String type;

    public Declaration(String identifier, String type) {
        this.identifier = identifier;
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Declaration that = (Declaration) o;
        return Objects.equals(identifier, that.identifier) && Objects.equals(type, that.type);

    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, type);
    }
}
