package interpreter.interpreters;

import ast.BooleanOperator;
import ast.Conditional;
import ast.IdentifierOperator;
import ast.interfaces.ValueNode;
import interpreter.Administrator;
import interpreter.Interpreter;
import interpreter.InterpreterFactory;
import interpreter.VariableData;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

public class ConditionalInterpreter implements InterpreterTypes<Conditional> {

    @Override
    public InterpreterResponse interpret(Conditional astNode, Administrator administrator) {
        Interpreter interpreter = InterpreterFactory.interpreterVersion(administrator, "1.1");
        try {
            boolean condition = getValue(astNode.getOperator(), administrator);

            if (condition) {
                return interpreter.interpretAST(astNode.getTrueBranch().iterator());
            } else {
                return astNode.getFalseBranch() != null ? interpreter.interpretAST(astNode.getFalseBranch().iterator()) : new SuccessResponse("Conditional is false and there is no else");
            }
        } catch (Exception e) {
            return new ErrorResponse("Error in conditional evaluation: " + e.getMessage());
        }
    }

    private boolean getValue(ValueNode value, Administrator administrator) throws Exception {
        if (value instanceof BooleanOperator booleanOperator) {
            return booleanOperator.getValue();
        } else if (value instanceof IdentifierOperator identifierOperator) {
            VariableData v = administrator.getVariable(identifierOperator.getIdentifier());
            String varValue = v.getValue();
            if ("true".equalsIgnoreCase(varValue)) {
                return true;
            } else if ("false".equalsIgnoreCase(varValue)) {
                return false;
            } else {
                throw new Exception("Identifier is not of boolean type");
            }
        } else {
            throw new IllegalStateException("Unexpected value type: " + value.getClass().getSimpleName());
        }
    }
}

