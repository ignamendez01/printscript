package parser.builder;

import ast.*;
import ast.interfaces.ValueNode;
import token.Token;

import java.util.*;

public class ValueASTBuilder implements ASTBuilder<ValueNode> {
    private final Map<String, Integer> precedence = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2
    );

    @Override
    public boolean verify(List<Token> statement) {
        boolean result = true;
        if (statement.isEmpty()) {
            result = false;
        }else {
            for (Token token : statement) {
                result = switch (token.getType()) {
                    case "ASSIGN", "DECLARE", "KEYWORD", "TYPE", "METHOD" -> false;
                    default -> true;
                };
            }
        }
        return result;
    }

    @Override
    public ValueNode build(List<Token> statement) {
        List<Token> rpnTokens = shuntingYard(statement);
        Deque<ValueNode> nodeStack = new ArrayDeque<>();

        int i = 0;
        while (i < rpnTokens.size()) {
            Token token = rpnTokens.get(i);
            switch (token.getType()) {
                case "NUMBER" -> {
                    double numberValue = Double.parseDouble(token.getValue());
                    if(numberValue % 1 == 0){
                        nodeStack.addLast(new NumberOperator((int) numberValue));
                    }else{
                        nodeStack.addLast(new NumberOperator(numberValue));
                    }
                }
                case "STRING" -> nodeStack.addLast(new StringOperator(token.getValue()));
                case "IDENTIFIER" -> nodeStack.addLast(new IdentifierOperator(token.getValue()));
                case "OPERATOR" -> {
                    ValueNode rightNode = nodeStack.removeLast();
                    ValueNode leftNode = nodeStack.removeLast();
                    if (leftNode instanceof StringOperator && rightNode instanceof NumberOperator) {
                        nodeStack.addLast(new BinaryOperation(leftNode, token.getValue(), new StringOperator(((NumberOperator) rightNode).getValue().toString())));
                    } else if (leftNode instanceof NumberOperator && rightNode instanceof StringOperator) {
                        nodeStack.addLast(new BinaryOperation(new StringOperator(((NumberOperator) leftNode).getValue().toString()), token.getValue(), rightNode));
                    } else {
                        nodeStack.addLast(new BinaryOperation(leftNode, token.getValue(), rightNode));
                    }
                }
                default ->
                        throw new RuntimeException("Unexpected token type: " + token.getType() + " at " + token.getPosition().y() + ":" + token.getPosition().x());
            }
            i++;
        }

        if (nodeStack.size() != 1) {
            throw new RuntimeException("Invalid expression: more than one node remaining in stack after parsing");
        }

        return nodeStack.getFirst();
    }

    private List<Token> shuntingYard(List<Token> tokens) {
        Deque<Token> outputQueue = new ArrayDeque<>();
        Deque<Token> operatorStack = new ArrayDeque<>();

        int i = 0;
        while (i < tokens.size()) {
            Token token = tokens.get(i);
            switch (token.getType()) {
                case "NUMBER", "STRING", "IDENTIFIER" -> outputQueue.addLast(token);
                case "OPERATOR" -> {
                    while (!operatorStack.isEmpty() && !Objects.equals(operatorStack.getLast().getType(), "LPAR") &&
                            precedence.get(operatorStack.getLast().getValue()) >= precedence.get(token.getValue())) {
                        outputQueue.addLast(operatorStack.removeLast());
                    }
                    operatorStack.addLast(token);
                }
                case "LPAR" -> operatorStack.addLast(token);
                case "RPAR" -> {
                    while (!operatorStack.isEmpty() && !Objects.equals(operatorStack.getLast().getType(), "LPAR")) {
                        outputQueue.addLast(operatorStack.removeLast());
                    }
                    if (operatorStack.isEmpty() || !Objects.equals(operatorStack.removeLast().getType(), "LPAR")) {
                        throw new RuntimeException("Mismatched parentheses in expression");
                    }
                }
                default ->
                        throw new RuntimeException("Unexpected token type: " + token.getType() + " at " + token.getPosition().y() + ":" + token.getPosition().x());
            }
            i++;
        }

        while (!operatorStack.isEmpty()) {
            Token operator = operatorStack.removeLast();
            if (Objects.equals(operator.getType(), "LPAR") || Objects.equals(operator.getType(), "RPAR")) {
                throw new RuntimeException("Mismatched parentheses in expression");
            }
            outputQueue.addLast(operator);
        }

        return new ArrayList<>(outputQueue);
    }
}

