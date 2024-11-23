import interpreter.Administrator;
import interpreter.Interpreter;
import interpreter.InterpreterFactory;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import lexer.Lexer;
import lexer.LexerFactory;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * el test del intrerpreter.
 */
public class InterpreterTest {
    private final Lexer lexer = LexerFactory.lexerVersion("1.0");
    private final Lexer lexer1 = LexerFactory.lexerVersion("1.1");
    private final Parser parser = ParserFactory.parserVersion("1.0");
    private final Parser parser1 = ParserFactory.parserVersion("1.1");
    private final Interpreter interpreter = InterpreterFactory.interpreterVersion(new Administrator(), "1.0");
    private final Interpreter interpreter1 = InterpreterFactory.interpreterVersion(new Administrator(), "1.1");

    @Test
    public void test_Sum_String_Number() throws Exception {
        String text =
                "let a:string = 'Hello'+5;" +
                "println(a);";

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello5", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Sum_Identifier_String() throws Exception {
        String text =
                "let a:number = 1;" +
                "let x: string = a+'Hello';" +
                "println(x);";

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("1Hello", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Multiply_Numbers() throws Exception {
        String text =
                "let a:number = 5*5;" +
                "println(a);";

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello World", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Assign_Declared_Value() throws Exception {
        String text = "let a:string;" +
                "a = 'Hello';" +
                "println(a);";

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Operation() throws Exception {
        String text =
                "let result:number = (5 + (5*10) - 2/2);" +
                "println(result);";

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter.interpretAST(parser.generateAST(lexer.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("54", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_true() throws Exception {
        String text = "let result : number;\nif(true){" +
                "\nresult = 12;\n} else {\nresult = 14;" +
                "\n}\nprintln(result);";

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(stream)));
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

        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_readInput() throws Exception {
        String text = "let result : number = readInput('Insert a number: ');" +
                "println(result);";
        String input = "14";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Insert a number: ", interpreter1.getAdmin().getPrintedElements().poll());
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_readEnv() throws Exception {
        String text = "let result : string = readEnv('NAME');" +
                "println(result);";
        InputStream stream = new ByteArrayInputStream(text.getBytes());

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(stream)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Ignacio", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_File() throws Exception {
        InputStream example = new FileInputStream("src/test/resources/testFile1.txt");

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(example)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello 20", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_File_2() throws Exception {
        InputStream example = new FileInputStream("src/test/resources/testFile2.txt");
        String input = "5";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        InterpreterResponse result = interpreter1.interpretAST(parser1.generateAST(lexer1.makeTokens(example)));
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Value to multiply", interpreter1.getAdmin().getPrintedElements().poll());
        assertEquals("Value 25", interpreter1.getAdmin().getPrintedElements().poll());
    }

}
