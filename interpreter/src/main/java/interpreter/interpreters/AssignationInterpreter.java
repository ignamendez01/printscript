package interpreter.interpreters;

import ast.DeclarationAssignation;
import ast.SimpleAssignation;
import ast.interfaces.Assignation;
import interpreter.VariableManager;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

public class AssignationInterpreter implements Interpreter<Assignation> {
    private final ValueInterpreter valueInterpreter;

    public AssignationInterpreter(ValueInterpreter valueInterpreter) {
        this.valueInterpreter = valueInterpreter;
    }

    @Override
    public InterpreterResponse interpret(Assignation astNode, VariableManager variableManager) {
        if (astNode instanceof DeclarationAssignation) {
            try {
                VariableResponse value = valueInterpreter.interpret(((DeclarationAssignation) astNode).getValue(), variableManager);
                variableManager.addVariable(((DeclarationAssignation) astNode).getDeclaration().getIdentifier(), ((DeclarationAssignation) astNode).getDeclaration().getType(), ((DeclarationAssignation) astNode).isConst(), value);
                return new SuccessResponse(null);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while declaring variable " + ((DeclarationAssignation) astNode).getDeclaration().getIdentifier());
            }
        } else if (astNode instanceof SimpleAssignation) {
            try {
                VariableResponse value = valueInterpreter.interpret(((SimpleAssignation) astNode).getValue(), variableManager);
                variableManager.setVariable(((SimpleAssignation) astNode).getIdentifier(), value);
                return new SuccessResponse(null);
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while setting variable " + ((SimpleAssignation) astNode).getIdentifier());
            }
        }
        return new ErrorResponse("Unsupported ASTNode type: " + astNode.getClass().getSimpleName());
    }
}
