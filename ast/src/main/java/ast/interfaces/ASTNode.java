package ast.interfaces;

import ast.BinaryOperation;
import ast.BooleanOperator;
import ast.Conditional;
import ast.Declaration;
import ast.DeclarationAssignation;
import ast.Function;
import ast.IdentifierOperator;
import ast.Method;
import ast.NumberOperator;
import ast.SimpleAssignation;
import ast.StringOperator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Interfaz para los nodos.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME, // Incluir el nombre del tipo
        include = JsonTypeInfo.As.PROPERTY, // Usar una propiedad para indicar el tipo
        property = "type" // Propiedad del JSON que contiene el nombre del tipo
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Declaration.class, name = "Declaration"),
        @JsonSubTypes.Type(value = DeclarationAssignation.class, name = "DeclarationAssignation"),
        @JsonSubTypes.Type(value = BinaryOperation.class, name = "BinaryOperation"),
        @JsonSubTypes.Type(value = BooleanOperator.class, name = "BooleanOperator"),
        @JsonSubTypes.Type(value = Conditional.class, name = "Conditional"),
        @JsonSubTypes.Type(value = Function.class, name = "Function"),
        @JsonSubTypes.Type(value = IdentifierOperator.class, name = "IdentifierOperator"),
        @JsonSubTypes.Type(value = Method.class, name = "Method"),
        @JsonSubTypes.Type(value = NumberOperator.class, name = "NumberOperator"),
        @JsonSubTypes.Type(value = StringOperator.class, name = "StringOperator"),
        @JsonSubTypes.Type(value = SimpleAssignation.class, name = "SimpleAssignation")
})
public interface ASTNode {
}
