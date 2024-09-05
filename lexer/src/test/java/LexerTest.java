import lexer.Lexer;
import org.junit.jupiter.api.Test;
import token.Token;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {
    Lexer lexer = Lexer.lexerVersion("1.0");
    Lexer lexer1 = Lexer.lexerVersion("1.1");

    @Test
    void test_Declare() {
        String example = "let a: number;";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(a), DECLARE, TYPE, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Assign() {
        String example = "x = 'Name ';";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[IDENTIFIER(x), ASSIGN, STRING('Name '), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_AssignDeclare() {
        String example = "let x: number = 12;";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(x), DECLARE, TYPE, ASSIGN, NUMBER(12), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Math() {
        String example = "x = -4 + 300 * 2.87 - 4 / 1;";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[IDENTIFIER(x), ASSIGN, NUMBER(-4), OPERATOR, NUMBER(300), OPERATOR, NUMBER(2.87), OPERATOR, NUMBER(4), OPERATOR, NUMBER(1), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Method() {
        String example = "println(x)";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[METHOD, LPAR, IDENTIFIER(x), RPAR]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_const() {
        String example = "const a:string = \"Hello\";";
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(a), DECLARE, TYPE, ASSIGN, STRING(\"Hello\"), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_boolean() {
        String example = "const a:boolean = false;";
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(a), DECLARE, TYPE, ASSIGN, BOOLEAN(false), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_readInput() {
        String example = "let a:number = readInput(\"A number over 10\");";
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(a), DECLARE, TYPE, ASSIGN, FUNCTION, LPAR, STRING(\"A number over 10\"), RPAR, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_readEnv() {
        String example = "let a:number = readEnv(\"A number over 10\");";
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(a), DECLARE, TYPE, ASSIGN, FUNCTION, LPAR, STRING(\"A number over 10\"), RPAR, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_if_else() {
        String example = "if(true){" +
                "a;" +
                "}else{" +
                "b;" +
                "}";
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[IF, LPAR, BOOLEAN(true), RPAR, LKEY, IDENTIFIER(a), END, RKEY, ELSE, LKEY, IDENTIFIER(b), END, RKEY]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_File() throws FileNotFoundException {
        InputStream example = new FileInputStream("src/test/resources/testFile1.txt");
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(x), DECLARE, TYPE, ASSIGN, STRING('Hello '), END, " +
                "KEYWORD, IDENTIFIER(y), DECLARE, TYPE, ASSIGN, NUMBER(5), OPERATOR, " +
                "LPAR, NUMBER(2), OPERATOR, NUMBER(10), RPAR, OPERATOR, " +
                "LPAR, NUMBER(15), OPERATOR, NUMBER(3), RPAR, END, " +
                "METHOD, LPAR, IDENTIFIER(x), OPERATOR, IDENTIFIER(y), RPAR, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_File2() throws FileNotFoundException {
        InputStream example = new FileInputStream("src/test/resources/testFile2.txt");
        List<Token> actualTokens = lexer1.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(x), DECLARE, TYPE, ASSIGN, STRING('Value '), END, " +
                "KEYWORD, IDENTIFIER(y), DECLARE, TYPE, ASSIGN, NUMBER(5), END, " +
                "KEYWORD, IDENTIFIER(z), DECLARE, TYPE, ASSIGN, BOOLEAN(true), END, " +
                "KEYWORD, IDENTIFIER(w), DECLARE, TYPE, ASSIGN, FUNCTION, LPAR, STRING(\"Value to multiply\"), RPAR, END, " +
                "IF, LPAR, IDENTIFIER(z), RPAR, LKEY, " +
                "IDENTIFIER(y), ASSIGN, IDENTIFIER(y), OPERATOR, IDENTIFIER(w), END, " +
                "RKEY, ELSE, LKEY, " +
                "IDENTIFIER(y), ASSIGN, IDENTIFIER(y), OPERATOR, FUNCTION, LPAR, STRING(\"NUMBER_TO_SUM\"), RPAR, END, " +
                "RKEY, " +
                "METHOD, LPAR, IDENTIFIER(x), OPERATOR, IDENTIFIER(y), RPAR, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Empty() {
        String example = "";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Unknown() {
        String example = "heyWorld = !;";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[IDENTIFIER(heyWorld), ASSIGN, UNKNOWN, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_MultipleLines() {
        String example = "let y: string; \n let x: number;";
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(y), DECLARE, TYPE, END, KEYWORD, IDENTIFIER(x), DECLARE, TYPE, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    private String listToString(List<Token> tokens) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < tokens.size(); i++) {
            sb.append(tokens.get(i).toString());
            if (i < tokens.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
