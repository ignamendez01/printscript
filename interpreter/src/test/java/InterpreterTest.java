import ast.*;
import ast.interfaces.ASTNode;
import interpreter.ExecuteInterpreter;
import interpreter.VariableManager;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {
    private final ExecuteInterpreter interpreter = ExecuteInterpreter.getDefaultInterpreter(new VariableManager());

    @BeforeEach
    public void setUp() {
        interpreter.getVariableManager().clear();
    }

    @Test
    public void test001_shouldInterpretAnASTAndExecuteIt() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "string"),
                        new BinaryOperation(
                                new StringOperator("Hello"),
                                "+",
                                new NumberOperator(5)
                        ),
                        false
                ),
                new Method("println", new BinaryOperation(new IdentifierOperator("a"), "+", new StringOperator("")))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("Hello5\n", successResult.getMessage());
    }

    @Test
    public void test002_shouldInterpretAnASTAndExecuteIt() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(1),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "+",
                                new StringOperator("Hello")
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("x"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("1Hello\n", successResult.getMessage());
    }

    @Test
    public void test003_shouldMultiplyTwoNumbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "*",
                                new NumberOperator(5)
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("a"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("25\n", successResult.getMessage());
    }

    @Test
    public void test004_shouldAddTwoNumbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(5),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("b", "number"),
                        new NumberOperator(5),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("c", "number"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "+",
                                new IdentifierOperator("b")
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("10\n", successResult.getMessage());
    }

    @Test
    public void test005_shouldSubtractTwoNumbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(10),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("b", "number"),
                        new NumberOperator(5),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("c", "number"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "-",
                                new IdentifierOperator("b")
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("5\n", successResult.getMessage());
    }

    @Test
    public void test006_shouldDivideTwoNumbers() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new NumberOperator(10),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("b", "number"),
                        new NumberOperator(5),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("c", "number"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "/",
                                new IdentifierOperator("b")
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("2\n", successResult.getMessage());
    }

    @Test
    public void test007_shouldConcatenateTwoStrings() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "string"),
                        new StringOperator("Hello"),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("b", "string"),
                        new StringOperator(" World"),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("c", "string"),
                        new BinaryOperation(
                                new IdentifierOperator("a"),
                                "+",
                                new IdentifierOperator("b")
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("c"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("Hello World\n", successResult.getMessage());
    }

    @Test
    public void test008_shouldInterpretADeclarationNode() throws Exception {
        List<ASTNode> ast = List.of(
                new Declaration("a", "string")
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test009_shouldInterpretASimpleAssignationNode() throws Exception {
        List<ASTNode> ast = List.of(
                new Declaration("a", "string"),
                new SimpleAssignation("a", new StringOperator("Hello"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test013_shouldInterpretAComplexNumberDeclarationWithAssignment() throws Exception {
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
                        ),
                        false
                ),
                new Method("println", new IdentifierOperator("result"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("54\n", successResult.getMessage());
    }

    @Test
    public void test014_shouldInterpretANumberDeclarationWithDecimalAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new NumberOperator(5.5),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test015_shouldInterpretANumberDeclarationWithDecimalAdditionAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("sum", "number"),
                        new BinaryOperation(
                                new NumberOperator(5.5),
                                "+",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test016_shouldInterpretANumberDeclarationWithDecimalSubtractionAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("diff", "number"),
                        new BinaryOperation(
                                new NumberOperator(10.5),
                                "-",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test017_shouldInterpretANumberDeclarationWithDecimalMultiplicationAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("prod", "number"),
                        new BinaryOperation(
                                new NumberOperator(5.5),
                                "*",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test018_shouldInterpretANumberDeclarationWithDecimalDivisionAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("quot", "number"),
                        new BinaryOperation(
                                new NumberOperator(10.5),
                                "/",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test019_shouldInterpretAComplexNumberDeclarationWithDecimalAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("result", "number"),
                        new BinaryOperation(
                                new BinaryOperation(
                                        new NumberOperator(5.5),
                                        "+",
                                        new BinaryOperation(
                                                new NumberOperator(5.5),
                                                "*",
                                                new NumberOperator(10.5)
                                        )
                                ),
                                "-",
                                new BinaryOperation(
                                        new NumberOperator(2.5),
                                        "/",
                                        new NumberOperator(2.5)
                                )
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test020_shouldThrowExceptionForUndeclaredVariable() throws Exception {
        List<ASTNode> ast = List.of(
                new SimpleAssignation("x", new NumberOperator(5.5)) // "x" is not declared
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable x not declared", errorResult.getMessage());
    }

    @Test
    public void test021_shouldThrowExceptionForRedeclarationOfVariable() throws Exception {
        List<ASTNode> ast = List.of(
                new Declaration("x", "number"),
                new Declaration("x", "number") // "x" is redeclared
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable x already declared", errorResult.getMessage());
    }

    @Test
    public void test022_shouldThrowExceptionForTypeMismatchInAssignment() throws Exception {
        List<ASTNode> ast = List.of(
                new Declaration("x", "number"),
                new SimpleAssignation("x", new StringOperator("Hello")) // "x" is a number, but we try to assign a string
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Type mismatch in variable x assignment", errorResult.getMessage());
    }

    @Test
    public void test023_shouldThrowExceptionForUnsupportedOperation() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new BinaryOperation(
                                new StringOperator("Hello"),
                                "-",
                                new StringOperator("World") // "-" operation is not supported for strings
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Unsupported operation: '-' with non-numeric operands", errorResult.getMessage());
    }

    @Test
    public void test024_shouldThrowExceptionForUnexpectedMethod() throws Exception {
        List<ASTNode> ast = List.of(
                new Method("unknownMethod", new NumberOperator(5)) // "unknownMethod" is not a supported method
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Unsupported method: unknownMethod", errorResult.getMessage());
    }

    @Test
    public void test026_shouldThrowExceptionForVariableAlreadyDeclared() throws Exception {
        List<ASTNode> ast = List.of(
                new Declaration("x", "number"),
                new DeclarationAssignation(
                        new Declaration("x", "number"), // "x" is already declared
                        new NumberOperator(5),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable x already declared", errorResult.getMessage());
    }

    @Test
    public void test027_shouldThrowExceptionForVariableNotDeclared() throws Exception {
        List<ASTNode> ast = List.of(
                new SimpleAssignation("x", new NumberOperator(5)) // "x" is not declared
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable x not declared", errorResult.getMessage());
    }

    @Test
    public void test029_shouldThrowExceptionForVariableNotDeclaredInOperation() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new IdentifierOperator("y"), // "y" is not declared
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable y not declared", errorResult.getMessage());
    }

    @Test
    public void test030_shouldThrowExceptionForUnsupportedOperationWithNonNumericOperands() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new BinaryOperation(
                                new StringOperator("Hello"),
                                "*",
                                new StringOperator("World") // "*" operation is not supported for strings
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Unsupported operation: '*' with non-numeric operands", errorResult.getMessage());
    }

    @Test
    public void test031_shouldThrowExceptionForUnsupportedOperationWithNonNumericOperandsInDivision() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new BinaryOperation(
                                new StringOperator("Hello"),
                                "/",
                                new StringOperator("World") // "/" operation is not supported for strings
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Unsupported operation: '/' with non-numeric operands", errorResult.getMessage());
    }

    @Test
    public void test032_shouldThrowExceptionForInvalidOperationSymbol() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "%", // "%" is not a valid operation
                                new NumberOperator(5)
                        ),
                        false
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("% is not a valid operation", errorResult.getMessage());
    }

    @Test
    public void test033_constAssignationShouldntBeAbleToOverride() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new NumberOperator(1),
                        true
                ),
                new SimpleAssignation(
                        "x",
                        new NumberOperator(10)
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable x is constant", errorResult.getMessage());
    }

    @Test
    public void test034_conditionalStatementTrue() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "boolean"),
                        new BooleanOperator("true"),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("x"),
                        List.of(
                                new Method("println", new StringOperator("true"))
                        ),
                        List.of(
                                new Method("println", new StringOperator("false"))
                        )
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("true\n", successResult.getMessage());
    }

    @Test
    public void test035_conditionalStatementFalse() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "boolean"),
                        new BooleanOperator("false"),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("x"),
                        List.of(
                                new Method("println", new StringOperator("true"))
                        ),
                        List.of(
                                new Method("println", new StringOperator("false"))
                        )
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("false\n", successResult.getMessage());
    }

    @Test
    public void test036_conditionalStatementFalseWithoutElseBody() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "boolean"),
                        new BooleanOperator("false"),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("x"),
                        List.of(
                                new Method("println", new StringOperator("true"))
                        ),
                        null
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertNull(successResult.getMessage());
    }

    @Test
    public void test037_whenDefineVariableInIfStatementToNotAppearOutOfScope() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "boolean"),
                        new BooleanOperator("true"),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("x"),
                        List.of(
                                new DeclarationAssignation(
                                        new Declaration("y", "number"),
                                        new NumberOperator(5),
                                        false
                                )
                        ),
                        null
                ),
                new Method("println", new IdentifierOperator("y"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable y not declared", errorResult.getMessage());
    }

    @Test
    public void test038_changeInIfStatementAVariableDeclaredOutOfTheScope() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new NumberOperator(5),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("y", "boolean"),
                        new BooleanOperator("true"),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("y"),
                        List.of(
                                new SimpleAssignation(
                                        "x",
                                        new NumberOperator(10)
                                )
                        ),
                        null
                ),
                new Method("println", new IdentifierOperator("x"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("10\n", successResult.getMessage());
    }

    @Test
    public void test039_whenVariableDefinedInOuterIfStatementShouldntBeAbleToDefineInInnerIfStatement() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "boolean"),
                        new BooleanOperator("true"),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("x"),
                        List.of(
                                new DeclarationAssignation(
                                        new Declaration("y", "number"),
                                        new NumberOperator(5),
                                        false
                                ),
                                new Conditional(
                                        new BooleanOperator("true"),
                                        List.of(
                                                new DeclarationAssignation(
                                                        new Declaration("y", "number"),
                                                        new NumberOperator(5),
                                                        false
                                                )
                                        ),
                                        null
                                )
                        ),
                        null
                ),
                new Method("println", new IdentifierOperator("y"))
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof ErrorResponse);
        ErrorResponse errorResult = (ErrorResponse) result;
        assertEquals("Variable y already declared", errorResult.getMessage());
    }

    @Test
    public void test040_whenVariableDefinedInOuterIfStatementShouldBeAbleToUseInInnerIfStatement() throws Exception {
        List<ASTNode> ast = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "boolean"),
                        new BooleanOperator("true"),
                        false
                ),
                new DeclarationAssignation(
                        new Declaration("y", "number"),
                        new NumberOperator(5),
                        false
                ),
                new Conditional(
                        new IdentifierOperator("x"),
                        List.of(
                                new Method("println", new IdentifierOperator("y"))
                        ),
                        null
                )
        );
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals("5\n", successResult.getMessage());
    }

    @Test
    public void test041_readInputFunction() throws Exception {
        List<ASTNode> ast = List.of(
                new Method("println", new Method("readInput", new StringOperator("Enter a value:")))
        );
        String input = "Test input";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InterpreterResponse result = interpreter.interpretAST(ast);
        assertTrue(result instanceof SuccessResponse);
        SuccessResponse successResult = (SuccessResponse) result;
        assertEquals(input + "\n", successResult.getMessage());
    }
}
