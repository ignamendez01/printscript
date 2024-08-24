package interpreter;

import ast.Declaration;
import ast.DeclarationAssignation;
import ast.Method;
import ast.SimpleAssignation;
import ast.interfaces.ASTNode;
import interpreter.interpreters.*;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

import java.util.List;
import java.util.Map;

public class Interpreter {
    private final Administrator admin;

    public Interpreter() {
        this.admin = new Administrator();
    }

    Map<Class<? extends ASTNode>, InterpreterTypes<? extends ASTNode>> visitors = Map.of(
            Declaration.class, new DeclarationInterpreter(),
            DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
            SimpleAssignation.class, new AssignationInterpreter(),
            Method.class, new MethodInterpreter()
    );

    public <T extends ASTNode> InterpreterResponse interpretAST(List<T> astList) throws Exception {
        String message = "";
        for (T ast : astList) {
            InterpreterTypes<T> interpreter = (InterpreterTypes<T>) visitors.get(ast.getClass());
            if (interpreter == null) {
                return new ErrorResponse("Unsupported ASTNode: " + ast.getClass().getSimpleName());
            }
            InterpreterResponse response = interpreter.interpret(ast, admin);
            if (response instanceof SuccessResponse) {
                message = ((SuccessResponse) response).message();
            } else if (response instanceof ErrorResponse) {
                return response;
            }
        }
        return new SuccessResponse(message);
    }
}