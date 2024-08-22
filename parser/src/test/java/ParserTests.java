import ast.*;
import ast.interfaces.ASTNode;
import lexer.Lexer;
import org.junit.jupiter.api.Test;
import parser.Parser;
import token.Token;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {

    @Test
    void test001_shouldConvertListOfTokensToAst() {
        String code = "let a: number = 5 * 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "*",
                                new NumberOperator(5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test002_shouldConvertListOfTokensToAstForPrintlnA() {
        String code = "println(a);";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new Method("println", new IdentifierOperator("a"))
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test003_shouldConvertListOfTokensToAstForLetXNumber() {
        String code = "let x: number;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new Declaration("x", "number")
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test005_shouldConvertListOfTokensToAstForLetYStringHello() {
        String code = "let y: string = 'Hello';";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("y", "string"),
                        new StringOperator("Hello"),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test006_shouldConvertListOfTokensToAstForLetZNumber5Dot5() {
        String code = "let z: number = 5.5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("z", "number"),
                        new NumberOperator(5.5),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test007_shouldConvertListOfTokensToAstForLetAStringHelloPlusWorld() {
        String code = "let a: string = 'Hello' + ' world!';";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("a", "string"),
                        new BinaryOperation(
                                new StringOperator("Hello"),
                                "+",
                                new StringOperator(" world!")
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test008_shouldConvertListOfTokensToAstForLetBStringHelloPlus5() {
        String code = "let b: string = 'Hello ' + 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("b", "string"),
                        new BinaryOperation(
                                new StringOperator("Hello "),
                                "+",
                                new StringOperator("5")
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test009_shouldConvertListOfTokensToAstForPrintln168() {
        String code = "println(168);";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new Method("println", new NumberOperator(168))
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test010_shouldConvertListOfTokensToAstForPrintlnAAgain() {
        String code = "println(a);";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new Method("println", new IdentifierOperator("a"))
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test011_shouldConvertListOfTokensToAstForLetXNumber5() {
        String code = "let x: number = 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("x", "number"),
                        new NumberOperator(5),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test012_shouldConvertListOfTokensToAstForLetSumNumber5Plus5() {
        String code = "let sum: number = 5 + 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("sum", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "+",
                                new NumberOperator(5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test013_shouldConvertListOfTokensToAstForLetDiffNumber10Minus5() {
        String code = "let diff: number = 10 - 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("diff", "number"),
                        new BinaryOperation(
                                new NumberOperator(10),
                                "-",
                                new NumberOperator(5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test014_shouldConvertListOfTokensToAstForLetProdNumber5Times5() {
        String code = "let prod: number = 5 * 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("prod", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "*",
                                new NumberOperator(5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test015_shouldConvertListOfTokensToAstForLetQuotNumber10Div5() {
        String code = "let quot: number = 10 / 5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("quot", "number"),
                        new BinaryOperation(
                                new NumberOperator(10),
                                "/",
                                new NumberOperator(5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test016_shouldConvertListOfTokensToAstForLetSumNumber5Dot5Plus5Dot5() {
        String code = "let sum: number = 5.5 + 5.5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("sum", "number"),
                        new BinaryOperation(
                                new NumberOperator(5.5),
                                "+",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test017_shouldConvertListOfTokensToAstForLetDiffNumber10Dot5Minus5Dot5() {
        String code = "let diff: number = 10.5 - 5.5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("diff", "number"),
                        new BinaryOperation(
                                new NumberOperator(10.5),
                                "-",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test018_shouldConvertListOfTokensToAstForLetProdNumber5Dot5Times5Dot5() {
        String code = "let prod: number = 5.5 * 5.5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("prod", "number"),
                        new BinaryOperation(
                                new NumberOperator(5.5),
                                "*",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test019_shouldConvertListOfTokensToAstForLetQuotNumber10Dot5Div5Dot5() {
        String code = "let quot: number = 10.5 / 5.5;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("quot", "number"),
                        new BinaryOperation(
                                new NumberOperator(10.5),
                                "/",
                                new NumberOperator(5.5)
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test020_shouldConvertListOfTokensToAstForLetResultNumber5Plus5Times10Minus2Div2() {
        String code = "let result: number = 5 + 5 * 10 - 2 / 2;";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("result", "number"),
                        new BinaryOperation(
                                new BinaryOperation(
                                        new NumberOperator(5),
                                        "+",
                                        new BinaryOperation(
                                                new NumberOperator(5),
                                                "*",
                                                new NumberOperator(10)
                                        )
                                ),
                                "-",
                                new BinaryOperation(
                                        new NumberOperator(2),
                                        "/",
                                        new NumberOperator(2)
                                )
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test021_shouldConvertListOfTokensToAstForPrintlnSum() {
        String code = "println(sum);";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new Method("println", new IdentifierOperator("sum"))
        );
        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test022_shouldConvertListOfTokensToAstForLetResultNumber5PlusProductOf5And10() {
        String code = "let result: number = 5 + (5 * 10);";
        var actualAst = getAstList(code);

        var expectedAst = List.of(
                new DeclarationAssignation(
                        new Declaration("result", "number"),
                        new BinaryOperation(
                                new NumberOperator(5),
                                "+",
                                new BinaryOperation(
                                        new NumberOperator(5),
                                        "*",
                                        new NumberOperator(10)
                                )
                        ),
                        false
                )
        );
        assertEquals(expectedAst, actualAst);
    }

    private static List<ASTNode> getAstList(String input) {
        Lexer lexer = Lexer.getDefaultLexer();
        TokenProvider tokenProvider = new TokenProvider(new ByteArrayInputStream(input.getBytes()), lexer);
        Parser parser = Parser.getDefaultParser();
        List<ASTNode> astList = new ArrayList<>();

        while (tokenProvider.hasNextStatement()) {
            List<Token> tokens = tokenProvider.readStatement();
            ASTNode ast = parser.generateAST(tokens);
            // Add the AST to the list if it is not null
            if (ast != null) {
                astList.add(ast);
            }
        }

        return astList;
    }
}

