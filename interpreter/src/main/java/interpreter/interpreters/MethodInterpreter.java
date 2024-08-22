package interpreter.interpreters;

import ast.Method;
import interpreter.VariableManager;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

public class MethodInterpreter implements Interpreter<Method> {
    private final ValueInterpreter valueInterpreter;

    public MethodInterpreter(ValueInterpreter valueInterpreter) {
        this.valueInterpreter = valueInterpreter;
    }

    @Override
    public InterpreterResponse interpret(Method astNode, VariableManager variableManager) {
        if ("println".equals(astNode.getIdentifier().toLowerCase())) {
            try {
                String value = valueInterpreter.interpret(astNode.getValue(), variableManager).getValue();
                return new SuccessResponse(value + "\n");
            } catch (Exception e) {
                return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while printing");
            }
        }
        return new ErrorResponse("Unsupported method: " + astNode.getIdentifier());
    }
}
