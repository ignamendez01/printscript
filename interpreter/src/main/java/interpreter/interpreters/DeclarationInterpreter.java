package interpreter.interpreters;

import ast.Declaration;
import interpreter.Administrator;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

/**
 * el interpretador de los declaration AST's.
 */
public class DeclarationInterpreter implements InterpreterTypes<Declaration> {

    @Override
    public InterpreterResponse interpret(Declaration astNode, Administrator administrator) {
        try {
            administrator.declareVariable(astNode.getIdentifier(), astNode.getType());
            return new SuccessResponse("Variable " + astNode.getIdentifier() + " was declared");
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage() != null ? e.getMessage() :
                    "Error while declaring variable " + astNode.getIdentifier());
        }
    }
}

