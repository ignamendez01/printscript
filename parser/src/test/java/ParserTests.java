import ast.*;
import ast.interfaces.ASTNode;
import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import token.Token;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {
    Lexer lexer = new Lexer();
    Parser parser = new Parser();


    @Test
    void test_Declare() {
        String code = "let x : number;";
        List<ASTNode> list = parser.generateAST(lexer.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new Declaration("x", "number")
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_Assign(){
        String code = "x = 'String';";
        List<ASTNode> list = parser.generateAST(lexer.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new SimpleAssignation("x", new StringOperator("String"))
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_DeclareAssignTree() {
        String code = "let a: number = 5 * 5;";
        List<ASTNode> list = parser.generateAST(lexer.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "*",
                                new NumberOperator(5)
                        )
                )
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_Method() {
        String code = "println(\"Hello World\");";
        List<ASTNode> list = parser.generateAST(lexer.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new Method(
                        "println",
                        new StringOperator("Hello World"
                        )
                )
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_File() throws FileNotFoundException {
        InputStream example = new FileInputStream("src/test/resources/testFile1.txt");
        List<ASTNode> list = parser.generateAST(lexer.makeTokens(example));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new StringOperator("Hello")
                ),
                new DeclarationAssignation(
                        new Declaration("y", "number"),
                        new NumberOperator(120)
                )
        );
        assertEquals(expectedAst, list);
    }
}

