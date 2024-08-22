package interpreter.interpreters;

import ast.interfaces.ASTNode;
import interpreter.VariableManager;
import interpreter.response.InterpreterResponse;

public interface Interpreter<T extends ASTNode> {
    InterpreterResponse interpret(T astNode, VariableManager variableManager) throws Exception;
}
