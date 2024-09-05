import org.junit.jupiter.api.Test;
import token.Position;
import token.Token;
import token.TokenCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTest {
    TokenCreator tokenCreator = new TokenCreator("token_types_1.0.txt");
    TokenCreator tokenCreator1 = new TokenCreator("token_types_1.1.txt");

    @Test
    public void test_Keyword_let() {

        Token expected = new Token("KEYWORD", "let", new Position(1,1));
        Token result = tokenCreator.createToken("let",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Keyword_const() {

        Token expected = new Token("KEYWORD", "const", new Position(1,1));
        Token result = tokenCreator1.createToken("const",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Variable() {

        Token expected = new Token("IDENTIFIER", "x", new Position(1,1));
        Token result = tokenCreator.createToken("x",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Declaration() {

        Token expected = new Token("DECLARE", ":", new Position(1,1));
        Token result = tokenCreator.createToken(":",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_String_type() {

        Token expected = new Token("TYPE", "string", new Position(1,1));
        Token result = tokenCreator.createToken("string",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Number_type() {

        Token expected = new Token("TYPE", "number", new Position(1,1));
        Token result = tokenCreator.createToken("number",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_type() {

        Token expected = new Token("TYPE", "boolean", new Position(1,1));
        Token result = tokenCreator1.createToken("boolean",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Assign() {

        Token expected = new Token("ASSIGN", "=", new Position(1,1));
        Token result = tokenCreator.createToken("=",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_String() {

        Token expected = new Token("STRING", "\"Hello\"", new Position(1,1));
        Token result = tokenCreator.createToken("\"Hello\"",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Number() {

        Token expected = new Token("NUMBER", "-4.4", new Position(1,1));
        Token result = tokenCreator.createToken("-4.4",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_true() {

        Token expected = new Token("BOOLEAN", "true", new Position(1,1));
        Token result = tokenCreator1.createToken("true",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Boolean_false() {

        Token expected = new Token("BOOLEAN", "false", new Position(1,1));
        Token result = tokenCreator1.createToken("false",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Plus() {

        Token expected = new Token("OPERATOR", "+", new Position(1,1));
        Token result = tokenCreator.createToken("+",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Minus() {

        Token expected = new Token("OPERATOR", "-", new Position(1,1));
        Token result = tokenCreator.createToken("-",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Multiply() {

        Token expected = new Token("OPERATOR", "*", new Position(1,1));
        Token result = tokenCreator.createToken("*",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Divide() {

        Token expected = new Token("OPERATOR", "/", new Position(1,1));
        Token result = tokenCreator.createToken("/",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Lpar() {

        Token expected = new Token("LPAR", "(", new Position(1,1));
        Token result = tokenCreator.createToken("(",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Rpar() {

        Token expected = new Token("RPAR", ")", new Position(1,1));
        Token result = tokenCreator.createToken(")",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_End() {

        Token expected = new Token("END", ";", new Position(1,1));
        Token result = tokenCreator.createToken(";",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Method() {

        Token expected = new Token("METHOD", "println", new Position(1,1));
        Token result = tokenCreator.createToken("println",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Unknown() {

        Token expected = new Token("UNKNOWN", "$", new Position(1,1));
        Token result = tokenCreator1.createToken("$",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_if() {

        Token expected = new Token("IF", "if", new Position(1,1));
        Token result = tokenCreator1.createToken("if",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_else() {

        Token expected = new Token("ELSE", "else", new Position(1,1));
        Token result = tokenCreator1.createToken("else",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Lkey() {

        Token expected = new Token("LKEY", "{", new Position(1,1));
        Token result = tokenCreator1.createToken("{",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Rkey() {

        Token expected = new Token("RKEY", "}", new Position(1,1));
        Token result = tokenCreator1.createToken("}",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Function_readEnv() {

        Token expected = new Token("FUNCTION", "readEnv", new Position(1,1));
        Token result = tokenCreator1.createToken("readEnv",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }

    @Test
    public void test_Function_readInput() {

        Token expected = new Token("FUNCTION", "readInput", new Position(1,1));
        Token result = tokenCreator1.createToken("readInput",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
    }
}
