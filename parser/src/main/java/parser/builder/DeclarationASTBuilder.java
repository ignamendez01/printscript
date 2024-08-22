package parser.builder;

import ast.Declaration;
import ast.DeclarationAssignation;
import ast.interfaces.ASTNode;
import token.Token;

import java.util.List;
import java.util.Objects;

public class DeclarationASTBuilder implements ASTBuilder<ASTNode> {
    private final ValueASTBuilder valueASTBuilder = new ValueASTBuilder();

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
            return new DeclarationAssignation(
                    new Declaration(statement.get(1).getValue(), statement.get(3).getValue()),
                    valueASTBuilder.build(statement.subList(5, statement.size()))
            );
        }else{
            return new Declaration(statement.get(1).getValue(), statement.get(3).getValue());
        }
    }
}

