package interpreter.interpreters;

import ast.Method;
import interpreter.Administrator;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

public class MethodInterpreter implements InterpreterTypes<Method> {
    ValueInterpreter valueInterpreter = new ValueInterpreter();

    @Override
    public InterpreterResponse interpret(Method astNode, Administrator administrator) {
        if ("println".equalsIgnoreCase(astNode.getIdentifier())) {
            try {
                VariableResponse response = (VariableResponse) valueInterpreter.interpret(astNode.getValue(), administrator);
                administrator.addPrinted(response.value());
                System.out.println(response.value());
                return new SuccessResponse("printed "+ response.value());
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while printing");
            }
        }
        return new ErrorResponse("Unsupported method: " + astNode.getIdentifier());
    }
}
