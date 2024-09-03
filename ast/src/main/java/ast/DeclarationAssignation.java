package ast;

import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;

import java.util.Objects;

// DeclarationAssignation (let x: number = 5;)
public class DeclarationAssignation implements ASTNode {
    private final Declaration declaration;
    private final ValueNode value;
    private final boolean isConst;

    public DeclarationAssignation(Declaration declaration, ValueNode value) {
        this.declaration = declaration;
        this.value = value;
        this.isConst = false;
    }

    public DeclarationAssignation(Declaration declaration, ValueNode value, boolean isConst) {
        this.declaration = declaration;
        this.value = value;
        this.isConst = isConst;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public ValueNode getValue() {
        return value;
    }

    public boolean isConst() {
        return isConst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeclarationAssignation that = (DeclarationAssignation) o;
        return Objects.equals(declaration, that.declaration) && Objects.equals(value, that.value) && Objects.equals(isConst, that.isConst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaration, value, isConst);
    }
}
