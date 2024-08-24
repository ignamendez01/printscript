package ast;

import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;

import java.util.Objects;

// DeclarationAssignation (let x: number = 5;)
public class DeclarationAssignation implements ASTNode {
    private final Declaration declaration;
    private final ValueNode value;

    public DeclarationAssignation(Declaration declaration, ValueNode value) {
        this.declaration = declaration;
        this.value = value;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public ValueNode getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclarationAssignation that = (DeclarationAssignation) o;
        return Objects.equals(declaration, that.declaration) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaration, value);
    }
}
