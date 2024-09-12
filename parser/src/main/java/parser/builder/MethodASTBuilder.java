package parser.builder;

import ast.Method;
import token.Token;

import java.util.List;

public class MethodASTBuilder implements ASTBuilder<Method> {
    private final ValueASTBuilder valueASTBuilder;

    public MethodASTBuilder(String version) {
        this.valueASTBuilder = new ValueASTBuilder(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        return statement.size() > 3
                && "METHOD".equals(statement.get(0).getType())
                && "LPAR".equals(statement.get(1).getType())
                && "RPAR".equals(statement.getLast().getType())
                && valueASTBuilder.verify(statement.subList(2, statement.size() - 1));
    }

    @Override
    public Method build(List<Token> statement) {
        return new Method(statement.getFirst().getValue(),
                valueASTBuilder.build(statement.subList(2, statement.size() - 1)));
    }
}