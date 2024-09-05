package interpreter;

import interpreter.response.VariableResponse;

import java.util.*;

public class Administrator {
    private final HashMap<Variable, String> variables = new HashMap<>();
    private final Queue<String> printedElements = new LinkedList<>();

    public void addVariable(String identifier, String type, boolean isConst, VariableResponse response) {
        if(getVariable(identifier) != null){
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        if (!type.equalsIgnoreCase(response.type())) {
            throw new RuntimeException("Type mismatch in variable " + identifier + " assignment");
        }
        variables.put(new Variable(identifier, type, isConst), response.value());
    }

    public void declareVariable(String identifier, String type) {
        if(getVariable(identifier) != null){
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        variables.put(new Variable(identifier, type, false), null);
    }

    public void setVariable(String identifier, VariableResponse variable) {
        Variable v = getVariable(identifier);
        if (v == null) {
            throw new RuntimeException("Variable " + identifier + " not declared");
        }else{
            if (v.getType().equalsIgnoreCase(variable.type())) {
                if(!v.isConst()){
                    variables.replace(v,variable.value());
                }else{
                    throw new RuntimeException("Variable " + identifier + " is const");
                }
            }else{
                throw new RuntimeException("Variable " + identifier + " is not type " + variable.type());
            }
        }
    }

    public Variable getVariable(String identifier) {
        Variable variable = null;
        for (Variable var : variables.keySet()) {
                if (var.getIdentifier().equals(identifier)) {
                    variable = var;
                }
        }
        return variable;
    }

    public HashMap<Variable,String> getVariables() {
        return variables;
    }

    public Queue<String> getPrintedElements() {
        return printedElements;
    }

    public void addPrinted(String value) {
        printedElements.add(value);
    }
}
