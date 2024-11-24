package interpreter.interpreters;

import ast.*;
import ast.interfaces.ValueNode;
import interpreter.Administrator;
import interpreter.VariableData;
import interpreter.response.InterpreterResponse;
import interpreter.response.VariableResponse;

import java.util.Objects;
import java.util.Scanner;

public class ValueInterpreter implements InterpreterTypes<ValueNode> {

    @Override
    public InterpreterResponse interpret(ValueNode astNode, Administrator administrator) throws Exception {
        if (astNode instanceof StringOperator) {
            StringOperator stringOperator = (StringOperator) astNode;
            return new VariableResponse("String", stringOperator.getValue());
        } else if (astNode instanceof NumberOperator) {
            NumberOperator numberOperator = (NumberOperator) astNode;
            return new VariableResponse("Number", numberOperator.getValue().toString());
        } else if (astNode instanceof BooleanOperator) {
            BooleanOperator booleanOperator = (BooleanOperator) astNode;
            return new VariableResponse("Boolean", booleanOperator.getValue().toString());
        } else if (astNode instanceof IdentifierOperator) {
            IdentifierOperator identifierOperator = (IdentifierOperator) astNode;
            return interpretIdentifierOperator(identifierOperator, administrator);
        } else if (astNode instanceof Function) {
            Function function = (Function) astNode;
            return interpretFunction(function, administrator);
        } else if (astNode instanceof BinaryOperation) {
            BinaryOperation binaryOperation = (BinaryOperation) astNode;
            return interpretBinaryOperation(binaryOperation, administrator);
        } else {
            throw new IllegalArgumentException("Unexpected node type: " + astNode.getClass().getSimpleName());
        }
    }

    private InterpreterResponse interpretIdentifierOperator(IdentifierOperator identifierOperator, Administrator administrator) {
        VariableData v = administrator.getVariable(identifierOperator.getIdentifier());
        String value = v.getValue();
        return new VariableResponse(v.getType(), value);
    }

    private InterpreterResponse interpretFunction(Function functionNode, Administrator administrator) {
        if (Objects.equals(functionNode.getName(), "readEnv")) {
            return readEnvVariable(functionNode);
        } else if (Objects.equals(functionNode.getName(), "readInput")) {
            return readInputMessage(functionNode, administrator);
        } else {
            throw new IllegalArgumentException("Unsupported method: " + functionNode.getName());
        }
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

        if (Objects.equals(binaryOperation.getSymbol(), "+")){
            return handleAddition(left, right);
        } else if (Objects.equals(binaryOperation.getSymbol(), "-")){
            return handleSubtraction(left, right);
        } else if (Objects.equals(binaryOperation.getSymbol(), "*")){
            return handleMultiplication(left, right);
        } else if (Objects.equals(binaryOperation.getSymbol(), "/")){
            return handleDivision(left, right);
        } else {
            throw new IllegalArgumentException(binaryOperation.getSymbol() + " is not a valid operation");
        }
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

