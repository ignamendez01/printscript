package interpreter.interpreters;

import ast.*;
import ast.interfaces.ValueNode;
import interpreter.Administrator;
import interpreter.VariableData;
import interpreter.response.InterpreterResponse;
import interpreter.response.VariableResponse;

import java.util.Scanner;

public class ValueInterpreter implements InterpreterTypes<ValueNode> {

    @Override
    public InterpreterResponse interpret(ValueNode astNode, Administrator administrator) throws Exception {
        switch (astNode) {
            case StringOperator stringOperator:
                return new VariableResponse("String", stringOperator.getValue());
            case NumberOperator numberOperator:
                return new VariableResponse("Number", numberOperator.getValue().toString());
            case BooleanOperator booleanOperator:
                return new VariableResponse("Boolean", booleanOperator.getValue().toString());
            case IdentifierOperator identifierOperator:
                return interpretIdentifierOperator(identifierOperator, administrator);
            case Function function:
                return interpretFunction(function, administrator);
            case BinaryOperation binaryOperation:
                return interpretBinaryOperation(binaryOperation, administrator);
            default :
                assert astNode != null;
                throw new IllegalArgumentException("Unexpected node type: " + astNode.getClass().getSimpleName());
        }
    }

    private InterpreterResponse interpretIdentifierOperator(IdentifierOperator identifierOperator, Administrator administrator) {
        VariableData v = administrator.getVariable(identifierOperator.getIdentifier());
        String value = v.getValue();
        return new VariableResponse(v.getType(), value);
    }

    private InterpreterResponse interpretFunction(Function functionNode, Administrator administrator) {
        return switch (functionNode.getName()) {
            case "readEnv" -> readEnvVariable(functionNode);
            case "readInput" -> readInputMessage(functionNode, administrator);
            default -> throw new IllegalArgumentException("Unsupported method: " + functionNode.getName());
        };
    }

    private InterpreterResponse readInputMessage(Function functionNode, Administrator administrator) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(functionNode.getMessage());
        administrator.addPrinted(functionNode.getMessage());
        String input = scanner.nextLine();
        if (input == null) {
            throw new IllegalArgumentException("Failed to read input");
        }
        return new VariableResponse(getType(input), input);
    }

    private InterpreterResponse readEnvVariable(Function functionNode) {
        String envValue = System.getenv(functionNode.getMessage());
        if (envValue == null) {
            throw new IllegalArgumentException("Environment variable " + functionNode.getMessage() + " does not exist");
        }
        return new VariableResponse(getType(envValue), envValue);
    }

    private InterpreterResponse interpretBinaryOperation(BinaryOperation binaryOperation, Administrator administrator) throws Exception {
        String left = ((VariableResponse) interpret(binaryOperation.getLeft(), administrator)).value();
        String right = ((VariableResponse) interpret(binaryOperation.getRight(), administrator)).value();

        return switch (binaryOperation.getSymbol()) {
            case "+" -> handleAddition(left, right);
            case "-" -> handleSubtraction(left, right);
            case "*" -> handleMultiplication(left, right);
            case "/" -> handleDivision(left, right);
            default -> throw new IllegalArgumentException(binaryOperation.getSymbol() + " is not a valid operation");
        };
    }

    private VariableResponse handleAddition(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double result = Double.parseDouble(left) + Double.parseDouble(right);
            return new VariableResponse("Number", formatNumber(result));
        } else {
            return new VariableResponse("String", left + right);
        }
    }

    private VariableResponse handleSubtraction(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double result = Double.parseDouble(left) - Double.parseDouble(right);
            return new VariableResponse("Number", formatNumber(result));
        } else {
            throw new IllegalArgumentException("Unsupported operation: '-' with non-numeric operands");
        }
    }

    private VariableResponse handleMultiplication(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double result = Double.parseDouble(left) * Double.parseDouble(right);
            return new VariableResponse("Number", formatNumber(result));
        } else {
            throw new IllegalArgumentException("Unsupported operation: '*' with non-numeric operands");
        }
    }

    private VariableResponse handleDivision(String left, String right) {
        if (isNumeric(left) && isNumeric(right)) {
            double result = Double.parseDouble(left) / Double.parseDouble(right);
            return new VariableResponse("Number", formatNumber(result));
        } else {
            throw new IllegalArgumentException("Unsupported operation: '/' with non-numeric operands");
        }
    }

    private boolean isNumeric(String value) {
        return value != null && value.matches("-?\\d+(\\.\\d+)?");
    }

    private String getType(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return "Boolean";
        } else if (isNumeric(value)) {
            return "Number";
        } else {
            return "String";
        }
    }

    private String formatNumber(double number) {
        return number % 1 == 0.0 ? Integer.toString((int) number) : Double.toString(number);
    }
}

