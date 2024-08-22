import lexer.Lexer;
import org.junit.jupiter.api.Test;
import token.Token;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LexerTest {

    @Test
    void test_Declare() {
        String example = "let a: number;";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(a), DECLARE, TYPE, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Assign() {
        String example = "x = 'Name';";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[IDENTIFIER(x), ASSIGN, STRING('Name'), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_AssignDeclare() {
        String example = "let x: number = 12;";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(x), DECLARE, TYPE, ASSIGN, NUMBER(12), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Math() {
        String example = "x = -4 + 300 * 2.87 - 4 / 1;";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[IDENTIFIER(x), ASSIGN, NUMBER(-4), OPERATOR, NUMBER(300), OPERATOR, NUMBER(2.87), OPERATOR, NUMBER(4), OPERATOR, NUMBER(1), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Method() {
        String example = "println(x)";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[METHOD, LPAR, IDENTIFIER(x), RPAR]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_File() throws FileNotFoundException {
        InputStream example = new FileInputStream("src/test/resources/testFile1.txt");
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[KEYWORD, IDENTIFIER(x), DECLARE, TYPE, ASSIGN, STRING('Hello'), END, KEYWORD, IDENTIFIER(y), DECLARE, TYPE, ASSIGN, NUMBER(120), END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Empty() {
        String example = "";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_Unknown() {
        String example = "heyWorld = !;";
        Lexer lexer = new Lexer();
        List<Token> actualTokens = lexer.makeTokens(example);

        String expectedTokensString = "[IDENTIFIER(heyWorld), ASSIGN, UNKNOWN, END]";
        String actualTokensString = listToString(actualTokens);

        assertEquals(expectedTokensString, actualTokensString);
    }

    @Test
    void test_MultipleLines() {
        String example = "let y: string; \n let x: number;";
        Lexer lexer = new Lexer();
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
