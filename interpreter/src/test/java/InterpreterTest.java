import ast.*;
import ast.interfaces.ASTNode;
import interpreter.Interpreter;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {
    private final Interpreter interpreter = Interpreter.interpreterVersion("1.0");

    @Test
    public void test_Sum_String_Number() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "string"),
                        new BinaryOperation(
                                new StringOperator("Hello"),
                                "+",
                                new NumberOperator(5)
                        )
                ),
                new Method("println", new BinaryOperation(new IdentifierOperator("a"), "+", new StringOperator("")))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("Hello5\n", successResult.message());
    }

    @Test
    public void test_Sum_Identifier_String() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(1)
                ),
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "+",
                                new StringOperator("Hello")
                        )
                ),
                new Method("println", new IdentifierOperator("x"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("1Hello\n", successResult.message());
    }

    @Test
    public void test_Multiply_Numbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "*",
                                new NumberOperator(5)
                        )
                ),
                new Method("println", new IdentifierOperator("a"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("25\n", successResult.message());
    }

    @Test
    public void test_Sum_Identifiers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(5)
                ),
                new DeclarationAssignation(
                        new Declaration("b", "number"),
                        new NumberOperator(5)
                ),
                new DeclarationAssignation(
                        new Declaration("c", "number"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "+",
                                new IdentifierOperator("b")
                        )
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("10\n", successResult.message());
    }

    @Test
    public void test_Subtract_Numbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(10)
                ),
                new DeclarationAssignation(
                        new Declaration("b", "number"),
                        new NumberOperator(5)
                ),
                new DeclarationAssignation(
                        new Declaration("c", "number"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "-",
                                new IdentifierOperator("b")
                        )
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("5\n", successResult.message());
    }

    @Test
    public void test_Divide_Numbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(10)
                ),
                new DeclarationAssignation(
                        new Declaration("b", "number"),
                        new NumberOperator(5)
                ),
                new DeclarationAssignation(
                        new Declaration("c", "number"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "/",
                                new IdentifierOperator("b")
                        )
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("2\n", successResult.message());
    }

    @Test
    public void test_shouldConcatenateTwoStrings() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "string"),
                        new StringOperator("Hello")
                ),
                new DeclarationAssignation(
                        new Declaration("b", "string"),
                        new StringOperator(" World")
                ),
                new DeclarationAssignation(
                        new Declaration("c", "string"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "+",
                                new IdentifierOperator("b")
                        )
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("Hello World\n", successResult.message());
    }

    @Test
    public void test_Assign_Declared_Value() throws Exception {
        List<ASTNode> ast = List.of(
                new Declaration("a", "string"),
                new SimpleAssignation("a", new StringOperator("Hello")),
                new Method("println", new IdentifierOperator("a"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("Hello\n", successResult.message());
    }

    @Test
    public void test_Operation() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("result", "number"),
                        new BinaryOperation(
                                new BinaryOperation(
                                        new NumberOperator(5),
                                        "+",
                                        new BinaryOperation(
                                                new NumberOperator(5),
                                                "*",
                                                new NumberOperator(10)
                                        )
                                ),
                                "-",
                                new BinaryOperation(
                                        new NumberOperator(2),
                                        "/",
                                        new NumberOperator(2)
                                )
                        )
                ),
                new Method("println", new IdentifierOperator("result"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertInstanceOf(SuccessResponse.class, result);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("54\n", successResult.message());
    }
}
