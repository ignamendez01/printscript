package ast;

import ast.interfaces.ASTNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.Objects;

/**
 * Representa una declaración.
 */
@JsonTypeName("Declaration")
public class Declaration implements ASTNode {
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("classType")
    private String classType;

    public Declaration() {
    }

    public Declaration(String identifier, String classType) {
        this.identifier = identifier;
        this.classType = classType;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getClassType() {
        return classType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Declaration that = (Declaration) o;
        return Objects.equals(identifier, that.identifier)
                && Objects.equals(classType, that.classType);

    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, classType);
    }
}
