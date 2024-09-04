import ast.*;
import ast.interfaces.ASTNode;
import interpreter.Interpreter;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InterpreterTest {
    private final Lexer lexer = Lexer.lexerVersion("1.0");
    private final Lexer lexer1 = Lexer.lexerVersion("1.1");
    private final Parser parser = Parser.parserVersion("1.0");
    private final Parser parser1 = Parser.parserVersion("1.1");
    private final Interpreter interpreter = Interpreter.interpreterVersion("1.0");
    private final Interpreter interpreter1 = Interpreter.interpreterVersion("1.1");

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
        assertEquals("Hello5", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Sum_Identifier_String() throws Exception {
        String text =
                "let a:number = 1;" +
                "let x: string = a+'Hello';" +
                "println(x);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("1Hello", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Multiply_Numbers() throws Exception {
        String text =
                "let a:number = 5*5;" +
                "println(a);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("25", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Sum_Identifiers() throws Exception {
        String text =
                "let a:number = 5;" +
                "let b:number = 5;" +
                "let c:number = a+ b;" +
                "println(c);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("10", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Subtract_Numbers() throws Exception {
        String text =
                "let a:number = 10;" +
                "let b:number = 5;" +
                "let c:number = a - b;" +
                "println(c);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("5", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Divide_Numbers() throws Exception {
        String text =
                "let a:number = 10;" +
                        "let b:number = 5;" +
                        "let c:number = a /b;" +
                        "println(c);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("2", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_shouldConcatenateTwoStrings() throws Exception {
        String text =
                "let a:string = \"Hello\";" +
                        "let b:string = ' World';" +
                        "let c:string = a + b;" +
                        "println(c);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello World", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Assign_Declared_Value() throws Exception {
        String text = "let a:string;" +
                "a = 'Hello';" +
                "println(a);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Operation() throws Exception {
        String text =
                "let result:number = (5 + (5*10) - 2/2);" +
                "println(result);";

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("54", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_true() throws Exception {
        String text =
                "let result : number;" +
                "if(true){" +
                        "result = 12;" +
                "} else {" +
                        "result = 14;" +
                "}" +
                "println(result);";

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("12", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_false() throws Exception {
        String text =
                "let result : number;" +
                        "if(false){" +
                        "result = 12;" +
                        "} else {" +
                        "result = 14;" +
                        "}" +
                        "println(result);";

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_one_tree_true() throws Exception {
        String text =
                "let result : number = 14;" +
                        "if(true){" +
                        "result = 12;" +
                        "}" +
                        "println(result);";

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("12", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_one_tree_false() throws Exception {
        String text =
                "let result : number = 14;" +
                        "if(false){" +
                        "result = 12;" +
                        "}" +
                        "println(result);";

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_readInput() throws Exception {
        String text = "let result : number = readInput('Insert a number: ');" +
                "println(result);";
        String input = "14";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_readEnv() throws Exception {
        String text = "let result : string = readEnv('NAME');" +
                "println(result);";

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(text)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Ignacio", interpreter1.getAdmin().getPrintedElements().poll());
    }
}
