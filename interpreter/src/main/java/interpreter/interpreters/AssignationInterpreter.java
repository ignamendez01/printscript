package interpreter.interpreters;

import ast.SimpleAssignation;
import interpreter.Administrator;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

public class AssignationInterpreter implements InterpreterTypes<SimpleAssignation> {
    ValueInterpreter valueInterpreter = new ValueInterpreter();

    @Override
    public InterpreterResponse interpret(SimpleAssignation astNode, Administrator administrator) {
        try {
            VariableResponse response = (VariableResponse) valueInterpreter.interpret(astNode.getValue(), administrator);
            administrator.setVariable(astNode.getIdentifier(), response);
            return new SuccessResponse("variable "+astNode.getIdentifier()+" was assigned value "+ response.value());
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while setting variable " + astNode.getIdentifier());
        }
    }
}
