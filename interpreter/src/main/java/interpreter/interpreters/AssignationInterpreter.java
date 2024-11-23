package interpreter.interpreters;

import ast.SimpleAssignation;
import interpreter.Administrator;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

/**
 * el interpretador de los assignation AST's.
 */
public class AssignationInterpreter implements InterpreterTypes<SimpleAssignation> {

    @Override
    public InterpreterResponse interpret(SimpleAssignation astNode, Administrator administrator) {
        ValueInterpreter valueInterpreter = new ValueInterpreter();
        try {
            VariableResponse response =
                    (VariableResponse) valueInterpreter.interpret(astNode.getValue(), administrator);
            administrator.setVariable(astNode.getIdentifier(), response);
            return new SuccessResponse("Variable " + astNode.getIdentifier() +
                    " was assigned value " + response.value());
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage() != null ? e.getMessage() :
                    "Error while setting variable " + astNode.getIdentifier());
        }
    }
}

