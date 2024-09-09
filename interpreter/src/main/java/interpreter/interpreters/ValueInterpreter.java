package interpreter.interpreters;

import ast.*;
import ast.interfaces.ValueNode;
import interpreter.Administrator;
import interpreter.Variable;
import interpreter.response.InterpreterResponse;
import interpreter.response.VariableResponse;

import java.util.Scanner;

public class ValueInterpreter implements InterpreterTypes<ValueNode>{

    @Override
    public InterpreterResponse interpret(ValueNode astNode, Administrator administrator) throws Exception {
        if(astNode instanceof StringOperator){
            return new VariableResponse("String", ((StringOperator) astNode).getValue());
        } else if (astNode instanceof NumberOperator) {
            return new VariableResponse("Number", ((NumberOperator) astNode).getValue().toString());
        }else if(astNode instanceof BooleanOperator){
            return new VariableResponse("Boolean", ((BooleanOperator) astNode).getValue().toString());
        }else if(astNode instanceof IdentifierOperator){
            Variable v = administrator.getVariable(((IdentifierOperator) astNode).getIdentifier());
            return new VariableResponse(v.getType(), administrator.getVariables().get(v));
        } else if (astNode instanceof Function) {
            switch (((Function) astNode).getName()){
                case "readEnv" -> {
                    return readEnvVariable(((Function) astNode));
                }
                case "readInput" -> {
                    return readInputMessage(((Function) astNode));
                }
                default -> throw new IllegalArgumentException("Unsupported method: " + ((Function) astNode).getName());
            }
        } else if(astNode instanceof BinaryOperation){
            String left = ((VariableResponse) interpret(((BinaryOperation) astNode).getLeft(), administrator)).value();
            String right = ((VariableResponse) interpret(((BinaryOperation) astNode).getRight(), administrator)).value();
            return switch (((BinaryOperation) astNode).getSymbol()) {
                case "+" -> handleAddition(left, right);
                case "-" -> handleSubtraction(left, right);
                case "*" -> handleMultiplication(left, right);
                case "/" -> handleDivision(left, right);
                default -> throw new Exception(((BinaryOperation) astNode).getSymbol() + " is not a valid operation");
            };
        }else{
            throw new Exception("Unexpected binary operation node");
        }
    }

    private VariableResponse readInputMessage(Function functionNode) {
        final Scanner scanner = new Scanner(System.in);
        System.out.println(functionNode.getMessage());
        String input = scanner.nextLine();
        if (input == null) {
            throw new IllegalArgumentException("Failed to read input");
        }
        String inputType = getType(input);
        return new VariableResponse(inputType, input);
    }

    private VariableResponse readEnvVariable(Function functionNode) {
        String envValue = System.getenv(functionNode.getMessage());
        if (envValue == null) {
            throw new IllegalArgumentException("Environment variable " + functionNode.getMessage() + " does not exist");
        }
        String envType = getType(envValue);
        return new VariableResponse(envType, envValue);
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

    private String getType(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return "Boolean";
        } else if (isNumeric(value)) {
            return "Number";
        } else {
            return "String";
        }
    }
}
