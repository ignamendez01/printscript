package parser.builder;

import ast.SimpleAssignation;
import ast.interfaces.Assignation;
import token.Token;

import java.util.List;
import java.util.Objects;

public class AssignationASTBuilder implements ASTBuilder<Assignation> {
    private final ValueASTBuilder valueASTBuilder = new ValueASTBuilder();

    @Override
    public boolean verify(List<Token> statement) {
        if (statement.isEmpty()) {
            return false;
        } else if (!Objects.equals(statement.get(0).getType(), "IDENTIFIER")) {
            return false;
        } else if (!Objects.equals(statement.get(1).getType(), "ASSIGN")) {
            return false;
        } else return valueASTBuilder.verify(statement.subList(2, statement.size()));
    }

    @Override
    public Assignation build(List<Token> statement) {
        return new SimpleAssignation(
                    statement.get(0).getValue(),
                    valueASTBuilder.build(statement.subList(2, statement.size()))
            );
    }
}

