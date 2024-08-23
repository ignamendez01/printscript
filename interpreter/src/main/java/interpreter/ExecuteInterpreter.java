package interpreter;

import ast.*;
import ast.interfaces.ASTNode;
import ast.interfaces.Assignation;
import interpreter.interpreters.*;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;

import java.util.Map;

public class ExecuteInterpreter {
    private final Map<Class<? extends ASTNode>, Interpreter<? extends ASTNode>> interpreters;
    private final VariableManager variableManager;

    public ExecuteInterpreter(Map<Class<? extends ASTNode>, Interpreter<? extends ASTNode>> interpreters, VariableManager variableManager) {
        this.interpreters = interpreters;
        this.variableManager = variableManager;
    }

    public ExecuteInterpreter(Map<Class<? extends ASTNode>, Interpreter<? extends ASTNode>> interpreters) {
        this(interpreters, new VariableManager());
    }

    public <T extends ASTNode> InterpreterResponse interpretAST(java.util.List<T> astList) throws Exception {
        StringBuilder stringBuffer = new StringBuilder();
        for (T ast : astList) {
            Interpreter<T> interpreter = (Interpreter<T>) interpreters.get(ast.getClass());
            if (interpreter == null) {
                return new ErrorResponse("Unsupported ASTNode: " + ast.getClass().getSimpleName());
            }

            InterpreterResponse response = interpreter.interpret(ast, variableManager);
            if (response instanceof SuccessResponse) {
                String message = ((SuccessResponse) response).getMessage();
                if (message != null) {
                    stringBuffer.append(message);
                }
            } else if (response instanceof ErrorResponse) {
                return response;
            }
        }
        return new SuccessResponse(stringBuffer.length() > 0 ? stringBuffer.toString() : null);
    }

    public VariableManager getVariableManager() {
        return variableManager;
    }

    public static ExecuteInterpreter getDefaultInterpreter(VariableManager variableManager) {
        ValueInterpreter valueInterpreter = new ValueInterpreter(new ConsoleReadInput(), new SystemReadEnv());
        Map<Class<? extends ASTNode>, Interpreter<? extends ASTNode>> visitors = Map.of(
                Declaration.class, new DeclarationInterpreter(),
                Assignation.class, new AssignationInterpreter(valueInterpreter),
                DeclarationAssignation.class, new AssignationInterpreter(valueInterpreter),
                SimpleAssignation.class, new AssignationInterpreter(valueInterpreter),
                Method.class, new MethodInterpreter(valueInterpreter),
                Conditional.class, new ConditionalInterpreter(valueInterpreter)
        );
        return new ExecuteInterpreter(visitors, variableManager);
    }

    public static ExecuteInterpreter getInterpreterByVersion(String version, ReadInputSource readInputSource, ReadEnvSource readEnvSource) {
        Map<Class<? extends ASTNode>, Interpreter<? extends ASTNode>> visitors = Map.of();
        switch (version) {
            case "1.0" -> {
                ValueInterpreter valueInterpreter = new ValueInterpreter(
                        expression -> { throw new RuntimeException("Invalid expression"); },
                        expression -> { throw new RuntimeException("Invalid expression"); }
                );
                visitors = Map.of(
                        Declaration.class, new DeclarationInterpreter(),
                        Assignation.class, new AssignationInterpreter(valueInterpreter),
                        DeclarationAssignation.class, new AssignationInterpreter(valueInterpreter),
                        SimpleAssignation.class, new AssignationInterpreter(valueInterpreter),
                        Method.class, new MethodInterpreter(valueInterpreter)
                );
            }
            case "1.1" -> {
                ValueInterpreter valueInterpreter = new ValueInterpreter(
                        readInputSource != null ? readInputSource : new ConsoleReadInput(),
                        readEnvSource != null ? readEnvSource : new SystemReadEnv()
                );
                visitors = Map.of(
                        Declaration.class, new DeclarationInterpreter(),
                        Assignation.class, new AssignationInterpreter(valueInterpreter),
                        DeclarationAssignation.class, new AssignationInterpreter(valueInterpreter),
                        SimpleAssignation.class, new AssignationInterpreter(valueInterpreter),
                        Method.class, new MethodInterpreter(valueInterpreter),
                        Conditional.class, new ConditionalInterpreter(valueInterpreter)
                );
            }
        }
        return new ExecuteInterpreter(visitors);
    }
}