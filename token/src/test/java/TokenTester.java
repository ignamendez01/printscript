import org.junit.jupiter.api.Test;
import token.Position;
import token.Token;
import token.TokenCreator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTester {

    @Test
    public void test_Keyword() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("KEYWORD", "let", new Position(1,1));
        Token result = tokenCreator.createToken("let",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Variable() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("IDENTIFIER", "x", new Position(1,1));
        Token result = tokenCreator.createToken("x",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Declaration() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("DECLARE", ":", new Position(1,1));
        Token result = tokenCreator.createToken(":",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_String_type() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("TYPE", "String", new Position(1,1));
        Token result = tokenCreator.createToken("String",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Number_type() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("TYPE", "Number", new Position(1,1));
        Token result = tokenCreator.createToken("Number",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Assign() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("ASSIGN", "=", new Position(1,1));
        Token result = tokenCreator.createToken("=",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_String() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("STRING", "\"Hello\"", new Position(1,1));
        Token result = tokenCreator.createToken("\"Hello\"",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Number() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("NUMBER", "-4.4", new Position(1,1));
        Token result = tokenCreator.createToken("-4.4",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Plus() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "+", new Position(1,1));
        Token result = tokenCreator.createToken("+",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Minus() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "-", new Position(1,1));
        Token result = tokenCreator.createToken("-",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Multiply() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "*", new Position(1,1));
        Token result = tokenCreator.createToken("*",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Divide() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("OPERATOR", "/", new Position(1,1));
        Token result = tokenCreator.createToken("/",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Lpar() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("LPAR", "(", new Position(1,1));
        Token result = tokenCreator.createToken("(",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Rpar() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("RPAR", ")", new Position(1,1));
        Token result = tokenCreator.createToken(")",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_End() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("END", ";", new Position(1,1));
        Token result = tokenCreator.createToken(";",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }

    @Test
    public void test_Method() {
        TokenCreator tokenCreator = new TokenCreator();

        Token expected = new Token("METHOD", "println", new Position(1,1));
        Token result = tokenCreator.createToken("println",1,1);

        assertEquals(expected.getType(), result.getType());
        assertEquals(expected.getValue(), result.getValue());
        assertEquals(expected.getPosition().getX(), result.getPosition().getX());
        assertEquals(expected.getPosition().getY(), result.getPosition().getY());
    }
}
