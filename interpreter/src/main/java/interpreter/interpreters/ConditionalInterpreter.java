package interpreter.interpreters;

import ast.Conditional;
import interpreter.Administrator;
import interpreter.Interpreter;
import interpreter.response.InterpreterResponse;

public class ConditionalInterpreter implements InterpreterTypes<Conditional> {
    @Override
    public InterpreterResponse interpret(Conditional astNode, Administrator administrator) throws Exception {
        Interpreter interpreter = Interpreter.interpreterVersion("1.1", administrator);

    }
}
