package parser.builder;

import ast.BooleanOperator;
import ast.Conditional;
import ast.IdentifierOperator;
import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;
import parser.Parser;
import parser.ParserFactory;
import token.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ConditionalASTBuilder implements ASTBuilder<Conditional> {

    @Override
    public boolean verify(List<Token> statement) {
        if (statement.size() <= 5) return false;
        if (!"IF".equals(statement.getFirst().getType())) return false;
        if (!"LPAR".equals(statement.get(1).getType())) return false;
        if (!("BOOLEAN".equals(statement.get(2).getType()) || "IDENTIFIER".equals(statement.get(2).getType()))) return false;
        if (!"RPAR".equals(statement.get(3).getType())) return false;
        if (!"LKEY".equals(statement.get(4).getType())) return false;
        return "RKEY".equals(statement.getLast().getType());
    }

    @Override
    public Conditional build(List<Token> statement) throws Exception {
        Parser parser = ParserFactory.parserVersion("1.1");
        int elsePosition = findElse(statement);

        ValueNode operator = buildOperator(statement);

        List<Token> trueBranchTokens = statement.subList(5, elsePosition != -1 ? elsePosition - 1 : statement.size() - 1);
        List<ASTNode> trueBranch = iteratorToList(parser.generateAST(trueBranchTokens.iterator()));

        List<ASTNode> falseBranch = elsePosition != -1
                ? iteratorToList(parser.generateAST(statement.subList(elsePosition + 2, statement.size() - 1).iterator()))
                : null;

        return new Conditional(operator, trueBranch, falseBranch);
    }

    private ValueNode buildOperator(List<Token> statement) {
        String tokenType = statement.get(2).getType();
        String tokenValue = statement.get(2).getValue();
        return "BOOLEAN".equals(tokenType)
                ? new BooleanOperator(transformBoolean(tokenValue))
                : new IdentifierOperator(tokenValue);
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

    private List<ASTNode> iteratorToList(Iterator<ASTNode> iterator){
        List<ASTNode> list = new ArrayList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }
}

