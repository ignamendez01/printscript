package parser.builder;

import ast.SimpleAssignation;
import ast.interfaces.Assignation;
import token.Token;

import java.util.List;

public class AssignationASTBuilder implements ASTBuilder<Assignation> {
    private final ValueASTBuilder valueASTBuilder;

    public AssignationASTBuilder(String version) {
        this.valueASTBuilder = new ValueASTBuilder(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        return !statement.isEmpty()
                && "IDENTIFIER".equals(statement.get(0).getType())
                && statement.size() > 1
                && "ASSIGN".equals(statement.get(1).getType())
                && valueASTBuilder.verify(statement.subList(2, statement.size()));
    }

    @Override
    public Assignation build(List<Token> statement) {
        return new SimpleAssignation(
                statement.getFirst().getValue(),
                valueASTBuilder.build(statement.subList(2, statement.size()))
        );
    }
}

