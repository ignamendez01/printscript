import ast.*;
import ast.interfaces.ASTNode;
import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {
    Lexer lexer = Lexer.lexerVersion("1.0");
    Lexer lexer1 = Lexer.lexerVersion("1.1");
    Parser parser = Parser.parserVersion("1.0");
    Parser parser1 = Parser.parserVersion("1.1");


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
    void test_const() {
        String code = "const x : string = 'Hello';";
        List<ASTNode> list = parser1.generateAST(lexer1.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new StringOperator("Hello"),
                        true
                )
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_const_1_0() {
        String code = "const x : string = 'Hello';";

        assertThrows(IllegalStateException.class, () -> parser.generateAST(lexer1.makeTokens(code)));
    }

    @Test
    void test_const_declare() {
        String code = "const x : string;";

        assertThrows(IllegalStateException.class, () -> parser1.generateAST(lexer1.makeTokens(code)));
    }

    @Test
    void test_If_Else() {
        String code = "if(true){" +
                "println(\"Hello World\");" +
                "}else{" +
                "println(\"Bye World\");" +
                "}";
        List<ASTNode> list = parser1.generateAST(lexer1.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new Conditional(
                        new BooleanOperator(true),
                        List.of(
                                new Method(
                                        "println",
                                        new StringOperator("Hello World")
                                )
                        ),
                        List.of(
                                new Method(
                                        "println",
                                        new StringOperator("Bye World")
                                )
                        ))
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_readEnv() {
        String code = "const x : string = readEnv(\"VARIABLE_KEY\");";
        List<ASTNode> list = parser1.generateAST(lexer1.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new Function("readEnv", "VARIABLE_KEY"),
                        true
                )
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_readInput() {
        String code = "let x : string = readInput(\"País favorito\");";
        List<ASTNode> list = parser1.generateAST(lexer1.makeTokens(code));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new Function("readInput", "País favorito"),
                        false
                )
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_File() throws FileNotFoundException {
        InputStream example = new FileInputStream("src/test/resources/testFile1.txt");
        List<ASTNode> list = parser1.generateAST(lexer1.makeTokens(example));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new StringOperator("Hello ")
                ),
                new DeclarationAssignation(
                        new Declaration("y", "number"),
                        new BinaryOperation(
                                new BinaryOperation(
                                        new NumberOperator(5),
                                        "+",
                                        new BinaryOperation(
                                                new NumberOperator(2),
                                                "*",
                                                new NumberOperator(10)
                                        )
                                ),
                                "-",
                                new BinaryOperation(
                                        new NumberOperator(15),
                                        "/",
                                        new NumberOperator(3)
                                ))
                ),
                new Method("println", new BinaryOperation(new IdentifierOperator("x"),
                        "+", new IdentifierOperator("y")))
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_File2() throws FileNotFoundException {
        InputStream example = new FileInputStream("src/test/resources/testFile2.txt");
        List<ASTNode> list = parser1.generateAST(lexer1.makeTokens(example));

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "string"),
                        new StringOperator("Value "),
                        true
                ),
                new DeclarationAssignation(
                        new Declaration("y", "number"),
                        new NumberOperator(5)
                ),
                new DeclarationAssignation(
                        new Declaration("z", "boolean"),
                        new BooleanOperator(true),
                        true
                ),
                new DeclarationAssignation(
                        new Declaration("w", "number"),
                        new Function("readInput","Value to multiply"),
                        true
                ),
                new Conditional(
                        new IdentifierOperator("z"),
                        List.of(
                             new SimpleAssignation(
                                     "y",
                                     new BinaryOperation(
                                             new IdentifierOperator("y"),
                                             "*",
                                             new IdentifierOperator("w")))
                        ),
                        List.of(
                             new SimpleAssignation(
                                     "y",
                                     new BinaryOperation(
                                             new IdentifierOperator("y"),
                                             "+",
                                             new Function(
                                                     "readEnv",
                                                     "NUMBER_TO_SUM"
                                             )))
                        )
                ),
                new Method("println", new BinaryOperation(new IdentifierOperator("x"),
                        "+", new IdentifierOperator("y")))
        );
        assertEquals(expectedAst, list);
    }
}

