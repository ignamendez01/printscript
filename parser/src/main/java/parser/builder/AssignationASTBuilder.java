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
        if (statement.size() < 3) return false;
        if (!"IDENTIFIER".equals(statement.getFirst().getType())) return false;
        if (!"ASSIGN".equals(statement.get(1).getType())) return false;

        List<Token> valueTokens = statement.subList(2, statement.size());
        return valueASTBuilder.verify(valueTokens);
    }

    @Override
    public Assignation build(List<Token> statement) {
        String identifier = statement.getFirst().getValue();

        List<Token> valueTokens = statement.subList(2, statement.size());

        return new SimpleAssignation(identifier, valueASTBuilder.build(valueTokens));
    }
}

