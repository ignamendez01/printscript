import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTest {
    TokenCreator tokenCreator = new TokenCreator("token_types_1.0.txt");
    TokenCreator tokenCreator1 = new TokenCreator("token_types_1.1.txt");

    @Test
    public void test_Keyword_let() {

        Token expected = new Token("KEYWORD", "let");
        Token result = tokenCreator.createToken("let");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Keyword_const() {

        Token expected = new Token("KEYWORD", "const");
        Token result = tokenCreator1.createToken("const");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Variable() {

        Token expected = new Token("IDENTIFIER", "x");
        Token result = tokenCreator.createToken("x");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Declaration() {

        Token expected = new Token("DECLARE", ":");
        Token result = tokenCreator.createToken(":");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_String_type() {

        Token expected = new Token("TYPE", "string");
        Token result = tokenCreator.createToken("string");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Number_type() {

        Token expected = new Token("TYPE", "number");
        Token result = tokenCreator.createToken("number");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_type() {

        Token expected = new Token("TYPE", "boolean");
        Token result = tokenCreator1.createToken("boolean");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Assign() {

        Token expected = new Token("ASSIGN", "=");
        Token result = tokenCreator.createToken("=");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_String() {

        Token expected = new Token("STRING", "\"Hello\"");
        Token result = tokenCreator.createToken("\"Hello\"");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Number() {

        Token expected = new Token("NUMBER", "-4.4");
        Token result = tokenCreator.createToken("-4.4");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_true() {

        Token expected = new Token("BOOLEAN", "true");
        Token result = tokenCreator1.createToken("true");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_false() {

        Token expected = new Token("BOOLEAN", "false");
        Token result = tokenCreator1.createToken("false");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Plus() {

        Token expected = new Token("OPERATOR", "+");
        Token result = tokenCreator.createToken("+");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Minus() {

        Token expected = new Token("OPERATOR", "-");
        Token result = tokenCreator.createToken("-");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Multiply() {

        Token expected = new Token("OPERATOR", "*");
        Token result = tokenCreator.createToken("*");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Divide() {

        Token expected = new Token("OPERATOR", "/");
        Token result = tokenCreator.createToken("/");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Lpar() {

        Token expected = new Token("LPAR", "(");
        Token result = tokenCreator.createToken("(");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Rpar() {

        Token expected = new Token("RPAR", ")");
        Token result = tokenCreator.createToken(")");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_End() {

        Token expected = new Token("END", ";");
        Token result = tokenCreator.createToken(";");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Method() {

        Token expected = new Token("METHOD", "println");
        Token result = tokenCreator.createToken("println");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Unknown() {

        Token expected = new Token("UNKNOWN", "$");
        Token result = tokenCreator1.createToken("$");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_if() {

        Token expected = new Token("IF", "if");
        Token result = tokenCreator1.createToken("if");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_else() {

        Token expected = new Token("ELSE", "else");
        Token result = tokenCreator1.createToken("else");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Lkey() {

        Token expected = new Token("LKEY", "{");
        Token result = tokenCreator1.createToken("{");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Rkey() {

        Token expected = new Token("RKEY", "}");
        Token result = tokenCreator1.createToken("}");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Function_readEnv() {

        Token expected = new Token("FUNCTION", "readEnv");
        Token result = tokenCreator1.createToken("readEnv");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Function_readInput() {

        Token expected = new Token("FUNCTION", "readInput");
        Token result = tokenCreator1.createToken("readInput");

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }
}
