import org.junit.jupiter.api.Test;
import token.Position;
import token.Token;
import token.TokenCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTester {

    @Test
    public void test_Keyword_let() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("KEYWORD", "let", new Position(1,1));
        Token result = tokenCreator.createToken("let",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Keyword_const() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("KEYWORD", "const", new Position(1,1));
        Token result = tokenCreator.createToken("const",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Variable() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("IDENTIFIER", "x", new Position(1,1));
        Token result = tokenCreator.createToken("x",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Declaration() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("DECLARE", ":", new Position(1,1));
        Token result = tokenCreator.createToken(":",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_String_type() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("TYPE", "string", new Position(1,1));
        Token result = tokenCreator.createToken("string",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Number_type() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("TYPE", "number", new Position(1,1));
        Token result = tokenCreator.createToken("number",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_type() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("TYPE", "boolean", new Position(1,1));
        Token result = tokenCreator.createToken("boolean",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Assign() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("ASSIGN", "=", new Position(1,1));
        Token result = tokenCreator.createToken("=",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_String() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("STRING", "\"Hello\"", new Position(1,1));
        Token result = tokenCreator.createToken("\"Hello\"",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Number() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("NUMBER", "-4.4", new Position(1,1));
        Token result = tokenCreator.createToken("-4.4",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_true() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("BOOLEAN", "true", new Position(1,1));
        Token result = tokenCreator.createToken("true",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_false() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("BOOLEAN", "false", new Position(1,1));
        Token result = tokenCreator.createToken("false",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Plus() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "+", new Position(1,1));
        Token result = tokenCreator.createToken("+",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Minus() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "-", new Position(1,1));
        Token result = tokenCreator.createToken("-",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Multiply() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "*", new Position(1,1));
        Token result = tokenCreator.createToken("*",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Divide() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "/", new Position(1,1));
        Token result = tokenCreator.createToken("/",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Lpar() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("LPAR", "(", new Position(1,1));
        Token result = tokenCreator.createToken("(",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Rpar() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("RPAR", ")", new Position(1,1));
        Token result = tokenCreator.createToken(")",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_End() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("END", ";", new Position(1,1));
        Token result = tokenCreator.createToken(";",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Method() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("METHOD", "println", new Position(1,1));
        Token result = tokenCreator.createToken("println",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Unknown() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("UNKNOWN", "$", new Position(1,1));
        Token result = tokenCreator.createToken("$",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_if() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("IF", "if", new Position(1,1));
        Token result = tokenCreator.createToken("if",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_else() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("ELSE", "else", new Position(1,1));
        Token result = tokenCreator.createToken("else",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Lkey() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("LKEY", "{", new Position(1,1));
        Token result = tokenCreator.createToken("{",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Rkey() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("RKEY", "}", new Position(1,1));
        Token result = tokenCreator.createToken("}",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Function_readEnv() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("FUNCTION", "readEnv", new Position(1,1));
        Token result = tokenCreator.createToken("readEnv",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Function_readInput() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("FUNCTION", "readInput", new Position(1,1));
        Token result = tokenCreator.createToken("readInput",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }
}
