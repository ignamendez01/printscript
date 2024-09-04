package interpreter.interpreters;

import ast.Conditional;
import interpreter.Administrator;
import interpreter.Interpreter;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

public class ConditionalInterpreter implements InterpreterTypes<Conditional> {
    @Override
    public InterpreterResponse interpret(Conditional astNode, Administrator administrator) throws Exception {
        Interpreter interpreter = Interpreter.interpreterVersion("1.1", administrator);
        if(astNode.getOperator().getValue()){
            return  interpreter.interpretAST(astNode.getTrueBranch());
        }else{
            if (astNode.getFalseBranch() != null) {
                return interpreter.interpretAST(astNode.getFalseBranch());
            }else{
                return new SuccessResponse("Conditional is false and there is no else");
            }
        }
    }
}
