import ast.*;
import ast.interfaces.ASTNode;
import lexer.Lexer;
import lexer.LexerFactory;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {
    Lexer lexer = LexerFactory.lexerVersion("1.0");
    Lexer lexer1 = LexerFactory.lexerVersion("1.1");
    Parser parser = ParserFactory.parserVersion("1.0");
    Parser parser1 = ParserFactory.parserVersion("1.1");


    @Test
    void test_Declare() throws Exception {
        String code = "let x : number;";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser.generateAST(lexer.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

        List<ASTNode> expectedAst = List.of(
                new Declaration("x", "number")
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_Assign() throws Exception {
        String code = "x = 'String';";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser.generateAST(lexer.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

        List<ASTNode> expectedAst = List.of(
                new SimpleAssignation("x", new StringOperator("String"))
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_DeclareAssignTree() throws Exception {
        String code = "let a: number = 5*5-8/4+2;";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser.generateAST(lexer.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

        List<ASTNode> expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new BinaryOperation(
                                new BinaryOperation(
                                      new BinaryOperation(
                                              new NumberOperator(5),
                                              "*",
                                              new NumberOperator(5)
                                      ),
                                      "-",
                                      new BinaryOperation(
                                              new NumberOperator(8),
                                              "/",
                                              new NumberOperator(4)
                                      )
                                ),
                                "+",
                                new NumberOperator(2)
                        )
                )
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_Method() throws Exception {
        String code = "println(\"Hello World\");";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser.generateAST(lexer.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

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
    void test_const() throws Exception {
        String code = "const x : string = 'Hello';";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser1.generateAST(lexer1.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

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
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        assertThrows(IllegalStateException.class, () -> parser.generateAST(lexer1.makeTokens(stream)));
    }

    @Test
    void test_const_declare() {
        String code = "const x : string;";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        assertThrows(IllegalStateException.class, () -> parser1.generateAST(lexer1.makeTokens(stream)));
    }

    @Test
    void test_If_Single() throws Exception {
        String code = "if(true){\nprintln(\"Hello World\");\n}\nprintln(1);";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser1.generateAST(lexer1.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

        List<ASTNode> expectedAst = List.of(
                new Conditional(
                        new BooleanOperator(true),
                        List.of(
                                new Method(
                                        "println",
                                        new StringOperator("Hello World")
                                )
                        ),
                        null),
                new Method("println", new NumberOperator(1))
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_If_Else() throws Exception {
        String code = """
                if(true){
                println("Hello World");
                }else{
                println("Bye World");
                }
                println(1);
                """;
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser1.generateAST(lexer1.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

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
                        )),
                new Method("println", new NumberOperator(1))
        );
        assertEquals(expectedAst, list);
    }

    @Test
    void test_readEnv() throws Exception {
        String code = "const x : string = readEnv(\"VARIABLE_KEY\");";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser1.generateAST(lexer1.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

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
    void test_readInput() throws Exception {
        String code = "let x : string = readInput(\"País favorito\");";
        InputStream stream = new ByteArrayInputStream(code.getBytes());

        Iterator<ASTNode> iterator = parser1.generateAST(lexer1.makeTokens(stream));
        List <ASTNode> list = iteratorToList(iterator);

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
    void test_File() throws Exception {
        InputStream example = new FileInputStream("src/test/resources/testFile1.txt");
        Iterator<ASTNode> iterator = parser.generateAST(lexer.makeTokens(example));
        List <ASTNode> list = iteratorToList(iterator);

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
    void test_File2() throws Exception {
        InputStream example = new FileInputStream("src/test/resources/testFile2.txt");
        Iterator<ASTNode> iterator = parser1.generateAST(lexer1.makeTokens(example));
        List <ASTNode> list = iteratorToList(iterator);

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

    private List<ASTNode> iteratorToList(Iterator<ASTNode> iterator){
        List<ASTNode> list = new ArrayList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }
}

