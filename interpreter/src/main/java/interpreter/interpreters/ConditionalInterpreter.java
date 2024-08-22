package interpreter.interpreters;

import ast.interfaces.ASTNode;
import interpreter.ExecuteInterpreter;
import interpreter.VariableManager;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import interpreter.response.VariableResponse;

import java.util.List;

public class ConditionalInterpreter implements Interpreter<Conditional> {
    private final ValueInterpreter valueInterpreter;

    public ConditionalInterpreter(ValueInterpreter valueInterpreter) {
        this.valueInterpreter = valueInterpreter;
    }

    @Override
    public InterpreterResponse interpret(Conditional astNode, VariableManager variableManager) {
        try {
            VariableResponse condition = valueInterpreter.interpret(astNode.getCondition(), variableManager);
            if (Boolean.parseBoolean(condition.getValue())) {
                variableManager.addScope();
                InterpreterResponse response = interpretAST(astNode.getThenBranch(), variableManager);
                variableManager.removeScope();
                return response;
            } else {
                if (astNode.getOtherwiseBranch() != null) {
                    variableManager.addScope();
                    InterpreterResponse response = interpretAST(astNode.getOtherwiseBranch(), variableManager);
                    variableManager.removeScope();
                    return response;
                }
                return new SuccessResponse(null);
            }
        } catch (Exception e) {
            return new ErrorResponse(e.getMessage() != null ? e.getMessage() : "Error while interpreting condition");
        }
    }

    private InterpreterResponse interpretAST(List<ASTNode> astList, VariableManager variableManager) throws Exception {
        ExecuteInterpreter interpreter = ExecuteInterpreter.getDefaultInterpreter(variableManager);
        return interpreter.interpretAST(astList);
    }
}

