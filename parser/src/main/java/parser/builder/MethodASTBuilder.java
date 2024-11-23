package parser.builder;

import ast.Method;
import token.Token;

import java.util.List;

/**
 * El constructor de Method AST´s.
 */
public class MethodASTBuilder implements ASTBuilder<Method> {
    private final ValueASTBuilder valueASTBuilder;

    public MethodASTBuilder(String version) {
        this.valueASTBuilder = new ValueASTBuilder(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        if (statement.size() <= 3) {
            return false;
        }
        if (!"METHOD".equals(statement.getFirst().getType())) {
            return false;
        }
        if (!"LPAR".equals(statement.get(1).getType())) {
            return false;
        }
        if (!"RPAR".equals(statement.getLast().getType())) {
            return false;
        }

        List<Token> valueTokens = statement.subList(2, statement.size() - 1);
        return valueASTBuilder.verify(valueTokens);
    }

    @Override
    public Method build(List<Token> statement) {
        String methodName = statement.getFirst().getValue();

        List<Token> valueTokens = statement.subList(2, statement.size() - 1);

        return new Method(methodName, valueASTBuilder.build(valueTokens));
    }
}
