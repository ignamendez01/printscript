package ast;

import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa una declaración y asignación.
 */
@JsonTypeName("DeclarationAssignation")
public class DeclarationAssignation implements ASTNode {
    @JsonProperty("declaration")
    private Declaration declaration;
    @JsonProperty("value")
    private ValueNode value;
    @JsonProperty("isConst")
    private boolean isConst;

    public DeclarationAssignation() {
    }

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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeclarationAssignation that = (DeclarationAssignation) o;
        return Objects.equals(declaration, that.declaration)
                && Objects.equals(value, that.value)
                && Objects.equals(isConst, that.isConst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(declaration, value, isConst);
    }
}
