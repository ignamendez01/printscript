package interpreter;

import ast.interfaces.ASTNode;
import interpreter.interpreters.InterpreterTypes;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

import java.util.Iterator;
import java.util.Map;

/**
 * El interpreter que lee e interpreta los AST'S y devuelve los resultados.
 */
public class Interpreter {
    private final Administrator admin;
    private final Map<Class<? extends ASTNode>, InterpreterTypes<? extends ASTNode>> visitors;

    public Interpreter(Administrator admin, Map<Class<? extends ASTNode>,
            InterpreterTypes<? extends ASTNode>> visitors) {
        this.admin = admin;
        this.visitors = visitors;
    }

    public <T extends ASTNode> InterpreterResponse interpretAST(Iterator<T> astIterator)
            throws Exception {
        while (astIterator.hasNext()) {
            T ast = astIterator.next();
            InterpreterTypes<T> interpreter = (InterpreterTypes<T>) visitors.get(ast.getClass());
            if (interpreter == null) {
                return new ErrorResponse("Unsupported ASTNode: " + ast.getClass().getSimpleName());
            }
            InterpreterResponse response = interpreter.interpret(ast, admin);
            if (response instanceof ErrorResponse) {
                return response;
            }
        }
        return new SuccessResponse("Interpretation completed");
    }

    public Administrator getAdmin() {
        return admin;
    }
}
