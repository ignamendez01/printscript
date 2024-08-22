package interpreter.interpreters;

import ast.Declaration;
import interpreter.VariableManager;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

public class DeclarationInterpreter implements Interpreter<Declaration> {

    @Override
    public InterpreterResponse interpret(Declaration astNode, VariableManager variableManager) {
        try {
            variableManager.declareVariable(astNode.getIdentifier(), astNode.getType());
            return new SuccessResponse(null);
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while declaring variable " + astNode.getIdentifier());
        }
    }
}
