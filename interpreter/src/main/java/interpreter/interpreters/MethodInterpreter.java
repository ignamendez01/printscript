package interpreter.interpreters;

import ast.Method;
import interpreter.Administrator;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

/**
 * el interpretador de los method AST's.
 */
public class MethodInterpreter implements InterpreterTypes<Method> {

    @Override
    public InterpreterResponse interpret(Method astNode, Administrator administrator) {
        ValueInterpreter valueInterpreter = new ValueInterpreter();

        if ("println".equalsIgnoreCase(astNode.getIdentifier())) {
            try {
                VariableResponse response =
                        (VariableResponse) valueInterpreter.interpret(astNode.getValue(), administrator);
                administrator.addPrinted(response.value());
                System.out.println(response.value());
                return new SuccessResponse("Printed " + response.value());
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage() != null ? e.getMessage() :
                        "Error while printing");
            }
        }
        return new ErrorResponse("Unsupported method: " + astNode.getIdentifier());
    }
}
