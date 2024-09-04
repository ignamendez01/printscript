package interpreter;

import ast.*;
import ast.interfaces.ASTNode;
import interpreter.interpreters.*;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Interpreter {
    private final Administrator admin;
    private final Map<Class<? extends ASTNode>, InterpreterTypes<? extends ASTNode>> visitors;

    private Interpreter(Administrator admin, Map<Class<? extends ASTNode>, InterpreterTypes<? extends ASTNode>> visitors) {
        this.admin = Objects.requireNonNullElseGet(admin, Administrator::new);
        this.visitors = visitors;
    }

    public static Interpreter interpreterVersion(String version){
        if (Objects.equals(version, "1.0")){
            return new Interpreter(null, Map.of(
                    Declaration.class, new DeclarationInterpreter(),
                    DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
                    SimpleAssignation.class, new AssignationInterpreter(),
                    Method.class, new MethodInterpreter()
            ));
        }else if (Objects.equals(version, "1.1")){
            return new Interpreter(null, Map.of(
                    Declaration.class, new DeclarationInterpreter(),
                    DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
                    SimpleAssignation.class, new AssignationInterpreter(),
                    Method.class, new MethodInterpreter(),
                    Conditional.class, new ConditionalInterpreter()
            ));
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }

    public static Interpreter interpreterVersion(String version, Administrator admin){
        if (Objects.equals(version, "1.0")){
            return new Interpreter(admin, Map.of(
                    Declaration.class, new DeclarationInterpreter(),
                    DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
                    SimpleAssignation.class, new AssignationInterpreter(),
                    Method.class, new MethodInterpreter()
            ));
        }else if (Objects.equals(version, "1.1")){
            return new Interpreter(admin, Map.of(
                    Declaration.class, new DeclarationInterpreter(),
                    DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
                    SimpleAssignation.class, new AssignationInterpreter(),
                    Method.class, new MethodInterpreter(),
                    Conditional.class, new ConditionalInterpreter()
            ));
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
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