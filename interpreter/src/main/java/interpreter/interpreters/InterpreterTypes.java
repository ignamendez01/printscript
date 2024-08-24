package interpreter.interpreters;

import ast.interfaces.ASTNode;
import interpreter.Administrator;
import interpreter.response.InterpreterResponse;

public interface InterpreterTypes<T extends ASTNode> {
    InterpreterResponse interpret(T astNode, Administrator administrator) throws Exception;
}
