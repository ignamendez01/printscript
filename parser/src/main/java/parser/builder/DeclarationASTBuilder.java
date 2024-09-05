package parser.builder;

import ast.*;
import ast.interfaces.ASTNode;
import token.Token;

import java.util.List;
import java.util.Objects;

public class DeclarationASTBuilder implements ASTBuilder<ASTNode> {
    private final String version;
    private final ValueASTBuilder valueASTBuilder;

    public DeclarationASTBuilder(String version) {
        this.version = version;
        this.valueASTBuilder = new ValueASTBuilder(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        if (statement.isEmpty()) {
            return false;
        } else if (!Objects.equals(statement.get(0).getType(), "KEYWORD")) {
            return false;
        } else if (!Objects.equals(statement.get(1).getType(), "IDENTIFIER")) {
            return false;
        } else if (!Objects.equals(statement.get(2).getType(), "DECLARE")) {
            return false;
        } else return Objects.equals(statement.get(3).getType(), "TYPE");
    }

    @Override
    public ASTNode build(List<Token> statement) {
        if(statement.size() > 4 && Objects.equals(statement.get(4).getType(), "ASSIGN")){
            return getDeclarationAssignation(statement);
        }else{
            return getDeclaration(statement);
        }
    }

    private Declaration getDeclaration(List<Token> statement) {
        if(Objects.equals(statement.getFirst().getValue(), "const")){
            if(Objects.equals(version, "1.0")){
                throw new IllegalStateException("Invalid keyword 'const'");
            }else{
                throw new IllegalStateException("A const variable should have a value assigned");
            }
        }else{
            return new Declaration(statement.get(1).getValue(), statement.get(3).getValue());
        }
    }

    private DeclarationAssignation getDeclarationAssignation(List<Token> statement) {
        if(Objects.equals(statement.getFirst().getValue(), "const")){
            if(Objects.equals(version, "1.0")){
                throw new IllegalStateException("Invalid keyword 'const'");
            }else{
                return new DeclarationAssignation(
                        new Declaration(statement.get(1).getValue(), statement.get(3).getValue()),
                        valueASTBuilder.build(statement.subList(5, statement.size())),
                        true
                );
            }
        }else{
            return new DeclarationAssignation(
                    new Declaration(statement.get(1).getValue(), statement.get(3).getValue()),
                    valueASTBuilder.build(statement.subList(5, statement.size())),
                    false
            );
        }
    }
}

