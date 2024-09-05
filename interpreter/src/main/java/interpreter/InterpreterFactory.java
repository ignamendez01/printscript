package interpreter;

import ast.*;
import interpreter.interpreters.*;

import java.util.Map;
import java.util.Objects;

public class InterpreterFactory {
    public static Interpreter interpreterVersion(Administrator admin, String version){
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
}
