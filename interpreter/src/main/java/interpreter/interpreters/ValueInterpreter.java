package interpreter.interpreters;

import ast.*;
import ast.interfaces.ValueNode;
import interpreter.Administrator;
import interpreter.Variable;
import interpreter.response.InterpreterResponse;
import interpreter.response.VariableResponse;

public class ValueInterpreter implements InterpreterTypes<ValueNode>{

    @Override
    public InterpreterResponse interpret(ValueNode astNode, Administrator administrator) throws Exception {
        switch (astNode) {
            case StringOperator stringOperator -> {
                return new VariableResponse("String", stringOperator.getValue());
            }
            case NumberOperator numberOperator -> {
                return new VariableResponse("Number", numberOperator.getValue().toString());
            }
            case IdentifierOperator identifierOperator -> {
                Variable v = administrator.getVariable(identifierOperator.getIdentifier());
                return new VariableResponse(v.type(), administrator.getVariables().get(v));
            }
            case BinaryOperation binaryNode -> {
                String left = ((VariableResponse) interpret(binaryNode.getLeft(), administrator)).value();
                String right = ((VariableResponse) interpret(binaryNode.getRight(), administrator)).value();
                return switch (binaryNode.getSymbol()) {
                    case "+" -> handleAddition(left, right);
                    case "-" -> handleSubtraction(left, right);
                    case "*" -> handleMultiplication(left, right);
                    case "/" -> handleDivision(left, right);
                    default -> throw new Exception(binaryNode.getSymbol() + " is not a valid operation");
                };
            }
            case null, default -> throw new Exception("Unexpected binary operation node");
        }
    }

    private VariableResponse handleAddition(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double numberValue = Double.parseDouble(left) + Double.parseDouble(right);
            if (numberValue % 1 == 0.0) {
                return new VariableResponse("Number", Integer.toString((int) numberValue));
            } else {
                return new VariableResponse("Number", Double.toString(numberValue));
            }
        } else {
            return new VariableResponse("String", left + right);
        }
    }

    private VariableResponse handleSubtraction(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double numberValue = Double.parseDouble(left) - Double.parseDouble(right);
            if (numberValue % 1 == 0.0) {
                return new VariableResponse("Number", Integer.toString((int) numberValue));
            } else {
                return new VariableResponse("Number", Double.toString(numberValue));
            }
        } else {
            throw new IllegalArgumentException("Unsupported operation: '-' with non-numeric operands");
        }
    }

    private VariableResponse handleMultiplication(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double numberValue = Double.parseDouble(left) * Double.parseDouble(right);
            if (numberValue % 1 == 0.0) {
                return new VariableResponse("Number", Integer.toString((int) numberValue));
            } else {
                return new VariableResponse("Number", Double.toString(numberValue));
            }
        } else {
            throw new IllegalArgumentException("Unsupported operation: '*' with non-numeric operands");
        }
    }

    private VariableResponse handleDivision(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double numberValue = Double.parseDouble(left) / Double.parseDouble(right);
            if (numberValue % 1 == 0.0) {
                return new VariableResponse("Number", Integer.toString((int) numberValue));
            } else {
                return new VariableResponse("Number", Double.toString(numberValue));
            }
        } else {
            throw new IllegalArgumentException("Unsupported operation: '/' with non-numeric operands");
        }
    }

    private boolean isNumeric(String value) {
        return value != null && value.matches("-?\\d+(\\.\\d+)?");
    }
}
