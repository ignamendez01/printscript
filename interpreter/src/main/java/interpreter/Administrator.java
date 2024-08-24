package interpreter;

import interpreter.response.VariableResponse;

import java.util.*;

public class Administrator {
    private final HashMap<Variable, String> variables = new HashMap<>();

    public void addVariable(String identifier, String type, VariableResponse response) {
        if(getVariable(identifier) != null){
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        if (!type.equalsIgnoreCase(response.type())) {
            throw new RuntimeException("Type mismatch in variable " + identifier + " assignment");
        }
        variables.put(new Variable(identifier, type), response.value());
    }

    public void declareVariable(String identifier, String type) {
        if(getVariable(identifier) != null){
            throw new RuntimeException("Variable " + identifier + " already declared");
        }
        variables.put(new Variable(identifier, type), null);
    }

    public void setVariable(String identifier, VariableResponse variable) {
        Variable v = getVariable(identifier);
        if (v == null) {
            throw new RuntimeException("Variable " + identifier + " not declared");
        }else{
            if (v.type().equalsIgnoreCase(variable.type())) {
                variables.replace(v,variable.value());
            }else{
                throw new RuntimeException("Variable " + identifier + " is not type " + variable.type());
            }
        }
    }

    public Variable getVariable(String identifier) {
        Variable variable = null;
        for (Variable var : variables.keySet()) {
                if (var.identifier().equals(identifier)) {
                    variable = var;
                }
        }
        return variable;
    }

    public HashMap<Variable,String> getVariables() {
        return variables;
    }
}
