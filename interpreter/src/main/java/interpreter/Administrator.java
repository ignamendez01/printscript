package interpreter;

import interpreter.response.VariableResponse;

import java.util.*;

public class Administrator {
    private final HashMap<String, VariableData> variables = new HashMap<>();
    private final Queue<String> printedElements = new LinkedList<>();
    private static final int MAX_PRINTED_SIZE = 100; // Limitar tamaño de la cola

    public void addVariable(String identifier, String type, boolean isConst, VariableResponse response) {
        if (variables.containsKey(identifier)) {
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        if (!type.equalsIgnoreCase(response.type())) {
            throw new RuntimeException("Type mismatch in variable " + identifier + " assignment");
        }
        variables.put(identifier, new VariableData(type, isConst, response.value()));
    }

    public void declareVariable(String identifier, String type) {
        if (variables.containsKey(identifier)) {
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        variables.put(identifier, new VariableData(type, false, null));
    }

    public void setVariable(String identifier, VariableResponse variable) {
        VariableData v = variables.get(identifier);
        if (v == null) {
            throw new RuntimeException("Variable " + identifier + " not declared");
        }
        if (v.getType().equalsIgnoreCase(variable.type())) {
            if (!v.isConst()) {
                v.setValue(variable.value());
            } else {
                throw new RuntimeException("Variable " + identifier + " is const");
            }
        } else {
            throw new RuntimeException("Variable " + identifier + " is not type " + variable.type());
        }
    }

    public VariableData getVariable(String identifier) {
        return variables.get(identifier);
    }

    public Queue<String> getPrintedElements() {
        return printedElements;
    }

    public void addPrinted(String value) {
        if (printedElements.size() >= MAX_PRINTED_SIZE) {
            printedElements.poll(); // Eliminar el más antiguo si supera el límite
        }
        printedElements.add(value);
    }
}

