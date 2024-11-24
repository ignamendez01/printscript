package parser.builder;

import ast.BinaryOperation;
import ast.BooleanOperator;
import ast.Function;
import ast.IdentifierOperator;
import ast.NumberOperator;
import ast.StringOperator;
import ast.interfaces.ValueNode;
import token.Token;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ValueASTBuilder implements ASTBuilder<ValueNode> {
    private final List<String> forbidden;
    private final Map<String, Integer> precedence = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2
    );

    public ValueASTBuilder(String version) {
        this.forbidden = ForbiddenListFactory.getList(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        if (statement.isEmpty()) return false;
        for (Token token : statement) {
            if (forbidden.contains(token.getType())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ValueNode build(List<Token> statement) {
        List<Token> reorganizedTokens = reorganize(statement);
        Deque<ValueNode> nodeStack = new ArrayDeque<>();

        for (int i = 0; i < reorganizedTokens.size(); i++) {
            Token token = reorganizedTokens.get(i);
            if (Objects.equals(token.getType(), "NUMBER")){
                handleNumber(token, nodeStack);
            } else if (Objects.equals(token.getType(), "STRING")){
                nodeStack.addLast(new StringOperator(removeQuotes(token.getValue())));
            } else if (Objects.equals(token.getType(), "IDENTIFIER")){
                nodeStack.addLast(new IdentifierOperator(token.getValue()));
            } else if (Objects.equals(token.getType(), "BOOLEAN")){
                handleBoolean(token, nodeStack);
            } else if (Objects.equals(token.getType(), "OPERATOR")){
                handleOperator(token, nodeStack);
            } else if (Objects.equals(token.getType(), "FUNCTION")){
                handleFunction(token, reorganizedTokens, nodeStack);
            } else {
                throw new RuntimeException("Unexpected token type: " + token.getType());
            }
        }

        if (nodeStack.size() != 1) {
            throw new RuntimeException("Invalid expression: more than one value node in stack.");
        }

        return nodeStack.getFirst();
    }

    private void handleNumber(Token token, Deque<ValueNode> nodeStack) {
        double numberValue = Double.parseDouble(token.getValue());
        if (numberValue % 1 == 0) {
            nodeStack.addLast(new NumberOperator((int) numberValue));
        } else {
            nodeStack.addLast(new NumberOperator(numberValue));
        }
    }

    private void handleBoolean(Token token, Deque<ValueNode> nodeStack) {
        boolean booleanValue = token.getValue().equals("true");
        nodeStack.addLast(new BooleanOperator(booleanValue));
    }

    private void handleOperator(Token token, Deque<ValueNode> nodeStack) {
        ValueNode rightNode = nodeStack.removeLast();
        ValueNode leftNode = nodeStack.removeLast();
        if (leftNode instanceof StringOperator && rightNode instanceof NumberOperator) {
            nodeStack.addLast(new BinaryOperation(leftNode, token.getValue(), new StringOperator(rightNode.toString())));
        } else if (leftNode instanceof NumberOperator && rightNode instanceof StringOperator) {
            nodeStack.addLast(new BinaryOperation(new StringOperator(leftNode.toString()), token.getValue(), rightNode));
        } else {
            nodeStack.addLast(new BinaryOperation(leftNode, token.getValue(), rightNode));
        }
    }

    private void handleFunction(Token token, List<Token> reorganizedTokens, Deque<ValueNode> nodeStack) {
        String functionName = token.getValue();
        int i = reorganizedTokens.indexOf(token);
        Token argumentToken = reorganizedTokens.get(i + 1);
        reorganizedTokens.remove(argumentToken);
        String argumentValue = removeQuotes(argumentToken.getValue());
        nodeStack.addLast(new Function(functionName, argumentValue));
    }

    private String removeQuotes(String value) {
        return value.substring(1, value.length() - 1);
    }

    private List<Token> reorganize(List<Token> tokens) {
        List<Token> outputList = new ArrayList<>();
        Deque<Token> operatorStack = new ArrayDeque<>();

        for (Token token : tokens) {
            if (token.getType().equals("NUMBER") || token.getType().equals("STRING") ||
                    token.getType().equals("IDENTIFIER") || token.getType().equals("BOOLEAN") ||
                    token.getType().equals("FUNCTION")) {
                outputList.add(token);
            } else if (token.getType().equals("OPERATOR")) {
                processOperator(token, operatorStack, outputList);
            } else if (token.getType().equals("LPAR")) {
                operatorStack.add(token);
            } else if (token.getType().equals("RPAR")) {
                processParenthesis(operatorStack, outputList);
            } else {
                throw new RuntimeException("Unexpected token type: " + token.getType());
            }
        }

        while (!operatorStack.isEmpty()) {
            Token operator = operatorStack.removeLast();
            if (isParenthesis(operator)) {
                throw new RuntimeException("Mismatched parentheses in expression");
            }
            outputList.add(operator);
        }

        return outputList;
    }

    private void processOperator(Token token, Deque<Token> operatorStack, List<Token> outputList) {
        while (!operatorStack.isEmpty() && precedence.getOrDefault(operatorStack.getLast().getValue(), 0) >= precedence.get(token.getValue())) {
            outputList.add(operatorStack.removeLast());
        }
        operatorStack.add(token);
    }

    private void processParenthesis(Deque<Token> operatorStack, List<Token> outputList) {
        while (!operatorStack.isEmpty() && !Objects.equals(operatorStack.getLast().getType(), "LPAR")) {
            outputList.add(operatorStack.removeLast());
        }
        if (operatorStack.isEmpty() || !Objects.equals(operatorStack.removeLast().getType(), "LPAR")) {
            throw new RuntimeException("Mismatched parentheses in expression");
        }
    }

    private boolean isParenthesis(Token token) {
        return Objects.equals(token.getType(), "LPAR") || Objects.equals(token.getType(), "RPAR");
    }

    private static class ForbiddenListFactory {
        public static List<String> getList(String version) {
            if (Objects.equals(version, "1.0")) {
                return Arrays.asList("ASSIGN", "DECLARE", "KEYWORD", "TYPE", "METHOD");
            } else {
                return Arrays.asList("ASSIGN", "DECLARE", "KEYWORD", "TYPE", "METHOD", "IF", "ELSE", "RKEY", "LKEY");
            }
        }
    }
}

