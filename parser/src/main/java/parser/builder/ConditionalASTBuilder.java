package parser.builder;

import ast.BooleanOperator;
import ast.Conditional;
import ast.IdentifierOperator;
import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;
import parser.Parser;
import parser.ParserFactory;
import token.Token;

import java.util.List;

public class ConditionalASTBuilder implements ASTBuilder<Conditional> {

    @Override
    public boolean verify(List<Token> statement) {
        return statement.size() > 5
                && "IF".equals(statement.get(0).getType())
                && "LPAR".equals(statement.get(1).getType())
                && ("BOOLEAN".equals(statement.get(2).getType()) || "IDENTIFIER".equals(statement.get(2).getType()))
                && "RPAR".equals(statement.get(3).getType())
                && "LKEY".equals(statement.get(4).getType())
                && "RKEY".equals(statement.getLast().getType());
    }

    @Override
    public Conditional build(List<Token> statement) throws Exception {
        Parser parser = ParserFactory.parserVersion("1.1");
        int elsePosition = findElse(statement);

        ValueNode operator = "BOOLEAN".equals(statement.get(2).getType())
                ? new BooleanOperator(transformBoolean(statement.get(2).getValue()))
                : new IdentifierOperator(statement.get(2).getValue());

        List<ASTNode> trueBranch = parser.generateAST(statement.subList(5, elsePosition != -1 ? elsePosition - 1 : statement.size() - 1).stream());
        List<ASTNode> falseBranch = elsePosition != -1
                ? parser.generateAST(statement.subList(elsePosition + 2, statement.size() - 1).stream())
                : null;

        return new Conditional(operator, trueBranch, falseBranch);
    }

    private boolean transformBoolean(String value) {
        return "true".equals(value);
    }

    private int findElse(List<Token> statement) {
        for (int i = 5; i < statement.size(); i++) {
            if ("ELSE".equals(statement.get(i).getType())) {
                return i;
            }
        }
        return -1;
    }
}
