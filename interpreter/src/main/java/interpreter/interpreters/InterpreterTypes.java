package interpreter.interpreters;

import ast.interfaces.ASTNode;
import interpreter.Administrator;
import interpreter.response.InterpreterResponse;

/**
 * La interfaz para todos los intérpretes.
 *
 * @param <T> el tipo de nodo AST que este intérprete puede procesar.
 */
public interface InterpreterTypes<T extends ASTNode> {
    InterpreterResponse interpret(T astNode, Administrator administrator) throws Exception;
}
