package interpreter;

import ast.interfaces.ASTNode;
import interpreter.interpreters.*;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

import java.util.List;
import java.util.Map;

public class Interpreter {
    private final Administrator admin;
    private final Map<Class<? extends ASTNode>, InterpreterTypes<? extends ASTNode>> visitors;

    public Interpreter(Administrator admin, Map<Class<? extends ASTNode>, InterpreterTypes<? extends ASTNode>> visitors) {
        this.admin = admin;
        this.visitors = visitors;
    }

    public <T extends ASTNode> InterpreterResponse interpretAST(List<T> astList) throws Exception {
        for (T ast : astList) {
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