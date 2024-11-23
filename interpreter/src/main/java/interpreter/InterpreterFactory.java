package interpreter;

import ast.Conditional;
import ast.Declaration;
import ast.DeclarationAssignation;
import ast.Method;
import ast.SimpleAssignation;
import interpreter.interpreters.AssignationInterpreter;
import interpreter.interpreters.ConditionalInterpreter;
import interpreter.interpreters.DeclarationAssignationInterpreter;
import interpreter.interpreters.DeclarationInterpreter;
import interpreter.interpreters.MethodInterpreter;

import java.util.Map;
import java.util.Objects;

/**
 * El creador de interpreter por version.
 */
public class InterpreterFactory {
    public static Interpreter interpreterVersion(Administrator admin, String version) {
        if (Objects.equals(version, "1.0")) {
            return new Interpreter(admin, Map.of(
                    Declaration.class, new DeclarationInterpreter(),
                    DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
                    SimpleAssignation.class, new AssignationInterpreter(),
                    Method.class, new MethodInterpreter()
            ));
        } else if (Objects.equals(version, "1.1")) {
            return new Interpreter(admin, Map.of(
                    Declaration.class, new DeclarationInterpreter(),
                    DeclarationAssignation.class, new DeclarationAssignationInterpreter(),
                    SimpleAssignation.class, new AssignationInterpreter(),
                    Method.class, new MethodInterpreter(),
                    Conditional.class, new ConditionalInterpreter()
            ));
        } else {
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }
}
