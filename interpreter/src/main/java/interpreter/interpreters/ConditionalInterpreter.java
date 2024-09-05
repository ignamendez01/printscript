package interpreter.interpreters;

import ast.BooleanOperator;
import ast.Conditional;
import ast.IdentifierOperator;
import ast.interfaces.ValueNode;
import interpreter.Administrator;
import interpreter.Interpreter;
import interpreter.InterpreterFactory;
import interpreter.Variable;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

import java.util.Objects;

public class ConditionalInterpreter implements InterpreterTypes<Conditional> {
    @Override
    public InterpreterResponse interpret(Conditional astNode, Administrator administrator) throws Exception {
        Interpreter interpreter = InterpreterFactory.interpreterVersion(administrator,"1.1");

        if(getValue(astNode.getOperator(), administrator)){
            return  interpreter.interpretAST(astNode.getTrueBranch());
        }else{
            if (astNode.getFalseBranch() != null) {
                return interpreter.interpretAST(astNode.getFalseBranch());
            }else{
                return new SuccessResponse("Conditional is false and there is no else");
            }
        }
    }

    private boolean getValue(ValueNode value, Administrator administrator){
        switch (value){
            case BooleanOperator booleanOperator -> {
                return booleanOperator.getValue();
            }
            case IdentifierOperator identifierOperator -> {
                Variable v = administrator.getVariable(identifierOperator.getIdentifier());
                return Objects.equals(administrator.getVariables().get(v), "true");
            }
            default -> throw new IllegalStateException("Unexpected value: " + value);
        }
    }
}
