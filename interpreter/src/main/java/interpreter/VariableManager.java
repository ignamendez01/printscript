package interpreter;

import interpreter.response.VariableResponse;

import java.util.*;

public class VariableManager {
    private final List<Map<Variable, String>> variablesStack = new ArrayList<>(Collections.singletonList(new HashMap<>()));

    public void addVariable(String identifier, String type, boolean isConst, VariableResponse value) {
        // Check if the variable is already declared in any scope
        if (variablesStack.stream().anyMatch(stack -> stack.keySet().stream().anyMatch(var -> var.getIdentifier().equals(identifier)))) {
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        // Checks if the type corresponds with the value
        if (!type.equalsIgnoreCase(value.getType())) {
            throw new RuntimeException("Type mismatch in variable " + identifier + " assignment");
        }
        // Assign the value of the assignation to the variable
        variablesStack.get(variablesStack.size() - 1).put(new Variable(identifier, type, isConst), value.getValue());
    }

    public void declareVariable(String identifier, String type) {
        // Check if the variable is already declared in any scope
        if (variablesStack.stream().anyMatch(stack -> stack.keySet().stream().anyMatch(var -> var.getIdentifier().equals(identifier)))) {
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        // Creates a new variable with the identifier and type of the declaration, and initializes it with null value (is a map)
        variablesStack.get(variablesStack.size() - 1).put(new Variable(identifier, type, false), null);
    }

    public void setVariable(String identifier, VariableResponse value) {
        // Look for the variable in the map
        Variable variable = getVariable(identifier);
        // Check if the variable is constant
        if (variable.isConst()) {
            throw new RuntimeException("Variable " + identifier + " is constant");
        }
        // Checks if the type corresponds with the value
        if (!variable.getType().equalsIgnoreCase(value.getType())) {
            throw new RuntimeException("Type mismatch in variable " + identifier + " assignment");
        }
        variablesStack.stream()
                .filter(stack -> stack.containsKey(variable))
                .findFirst()
                .ifPresent(stack -> stack.put(variable, value.getValue()));
    }

    public Map.Entry<Variable, String> getVariableWithValue(String identifier) {
        Variable variable = getVariable(identifier);
        String value = variablesStack.stream()
                .filter(stack -> stack.containsKey(variable))
                .map(stack -> stack.get(variable))
                .findFirst()
                .orElse(null);
        if (value == null) {
            throw new RuntimeException("Variable " + identifier + " not declared");
        }
        return new AbstractMap.SimpleEntry<>(variable, value);
    }

    public void addScope() {
        variablesStack.add(new HashMap<>());
    }

    public void removeScope() {
        if (!variablesStack.isEmpty()) {
            variablesStack.remove(variablesStack.size() - 1);
        }
    }

    public List<Map<Variable, String>> getVariablesStack() {
        return variablesStack;
    }

    public void clear() {
        variablesStack.clear();
        variablesStack.add(new HashMap<>());
    }

    private Variable getVariable(String identifier) {
        for (int i = variablesStack.size() - 1; i >= 0; i--) {
            Map<Variable, String> variables = variablesStack.get(i);
            for (Variable var : variables.keySet()) {
                if (var.getIdentifier().equals(identifier)) {
                    return var;
                }
            }
        }
        throw new RuntimeException("Variable " + identifier + " not declared");
    }
}
