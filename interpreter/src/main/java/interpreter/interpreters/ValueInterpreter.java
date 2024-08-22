package interpreter.interpreters;

import ast.*;
import ast.interfaces.ValueNode;
import interpreter.Variable;
import interpreter.VariableManager;
import interpreter.inputs.ReadEnvSource;
import interpreter.inputs.ReadInputSource;
import interpreter.response.VariableResponse;

import java.util.Map;

public class ValueInterpreter implements Interpreter<ValueNode> {
    private final ReadInputSource readInputSource;
    private final ReadEnvSource readEnvSource;

    public ValueInterpreter(ReadInputSource readInputSource, ReadEnvSource readEnvSource) {
        this.readInputSource = readInputSource;
        this.readEnvSource = readEnvSource;
    }

    @Override
    public VariableResponse interpret(ValueNode astNode, VariableManager variableManager) throws Exception {
        if (astNode instanceof StringOperator) {
            return new VariableResponse("String", ((StringOperator) astNode).getValue());
        } else if (astNode instanceof NumberOperator) {
            return new VariableResponse("Number", ((NumberOperator) astNode).getValue().toString());
        } else if (astNode instanceof BooleanOperator) {
            return new VariableResponse("Boolean", ((BooleanOperator) astNode).getValue());
        } else if (astNode instanceof IdentifierOperator) {
            Map.Entry<Variable, String> variable = variableManager.getVariableWithValue(((IdentifierOperator) astNode).getIdentifier());
            return new VariableResponse(variable.getKey().getType(), variable.getValue());
        } else if (astNode instanceof Method methodNode) {
            switch (methodNode.getIdentifier().toLowerCase()) {
                case "readenv" -> {
                    String argumentEnv = ((StringOperator) methodNode.getValue()).getValue();
                    String envValue = readEnvSource.readEnv(argumentEnv);
                    if (envValue == null) {
                        throw new IllegalArgumentException("Environment variable " + argumentEnv + " does not exist");
                    }
                    String envType = getType(envValue);
                    return new VariableResponse(envType, envValue);
                }
                case "readinput" -> {
                    String argumentInput = ((StringOperator) methodNode.getValue()).getValue();
                    String inputValue = readInputSource.readInput(argumentInput);
                    if (inputValue == null) {
                        throw new IllegalArgumentException("Failed to read input");
                    }
                    String inputType = getType(inputValue);
                    return new VariableResponse(inputType, inputValue);
                }
                default -> throw new IllegalArgumentException("Unsupported method: " + methodNode.getIdentifier());
            }
        } else if (astNode instanceof BinaryOperation binaryNode) {
            String left = interpret(binaryNode.getLeft(), variableManager).getValue();
            String right = interpret(binaryNode.getRight(), variableManager).getValue();
            return switch (binaryNode.getSymbol()) {
                case "+" -> handleAddition(left, right);
                case "-" -> handleSubtraction(left, right);
                case "*" -> handleMultiplication(left, right);
                case "/" -> handleDivision(left, right);
                default -> throw new Exception(binaryNode.getSymbol() + " is not a valid operation");
            };
        } else {
            throw new Exception("Unexpected binary operation node");
        }
    }

    private String getType(String value) {
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return "Boolean";
        } else if (value.matches("-?\\d+(\\.\\d+)?")) {
            return "Number";
        } else {
            return "String";
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
