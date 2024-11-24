package interpreter.interpreters;

import ast.DeclarationAssignation;
import interpreter.Administrator;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

/**
 * el interpretador de los Declarationassignation AST's.
 */
public class DeclarationAssignationInterpreter implements InterpreterTypes<DeclarationAssignation> {

    @Override
    public InterpreterResponse interpret(DeclarationAssignation astNode, Administrator administrator) {
        ValueInterpreter valueInterpreter = new ValueInterpreter();
        try {
            VariableResponse response =
                    (VariableResponse) valueInterpreter.interpret(astNode.getValue(), administrator);
            administrator.addVariable(astNode.getDeclaration().getIdentifier(),
                    astNode.getDeclaration().getClassType(), astNode.isConst(), response);
            return new SuccessResponse("Variable " + astNode.getDeclaration().getIdentifier() +
                    " was declared with value " + response.value());
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage() != null ? e.getMessage() :
                    "Error while declaring variable " + astNode.getDeclaration().getIdentifier());
        }
    }
}

