package parser.builder;

import ast.*;
import ast.BooleanOperator;
import ast.interfaces.ValueNode;
import token.Token;

import java.util.*;

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
        if (statement.isEmpty()) {
            return false;
        }
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
            switch (token.getType()) {
                case "NUMBER" -> handleNumber(token, nodeStack);
                case "STRING" -> nodeStack.addLast(new StringOperator(removeQuotes(token.getValue())));
                case "IDENTIFIER" -> nodeStack.addLast(new IdentifierOperator(token.getValue()));
                case "BOOLEAN" -> handleBoolean(token, nodeStack);
                case "OPERATOR" -> handleOperator(token, nodeStack);
                case "FUNCTION" -> handleFunction(token, reorganizedTokens, nodeStack, reorganizedTokens.indexOf(token));
                default -> throw new RuntimeException("Unexpected token type: " + token.getType());
            }
        }

        if (nodeStack.size() != 1) {
            throw new RuntimeException("Invalid expression: more than one node remaining in stack after parsing");
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

    private void handleFunction(Token token, List<Token> tokens, Deque<ValueNode> nodeStack, int position) {
        String functionName = token.getValue();
        // Aquí te aseguras de que el próximo token es un argumento
        String argumentValue = removeQuotes(tokens.get(position+1).getValue());
        tokens.remove(position+1);
        nodeStack.addLast(new Function(functionName, argumentValue));
    }

    private String removeQuotes(String value) {
        return value.substring(1, value.length() - 1);
    }

    private List<Token> reorganize(List<Token> tokens) {
        List<Token> outputList = new ArrayList<>();
        Deque<Token> operatorStack = new ArrayDeque<>();

        for (Token token : tokens) {
            switch (token.getType()) {
                case "NUMBER", "STRING", "IDENTIFIER", "BOOLEAN", "FUNCTION" -> outputList.add(token);
                case "OPERATOR" -> processOperator(token, operatorStack, outputList);
                case "LPAR" -> operatorStack.add(token);
                case "RPAR" -> processParenthesis(operatorStack, outputList);
                default -> throw new RuntimeException("Unexpected token type: " + token.getType());
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

