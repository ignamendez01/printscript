import ast.interfaces.ASTNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.ParserFactory;
import token.Token;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTests {
    Parser parser = ParserFactory.parserVersion("1.0");
    Parser parser1 = ParserFactory.parserVersion("1.1");


    @Test
    void test_Declare() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_Declare/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_Declare/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_Assign() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_Assign/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_Assign/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_DeclareAssignTree() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_DeclareAssignTree/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_DeclareAssignTree/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_Method() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_Method/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_Method/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_const() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_const/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser1.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_const/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_const_1_0() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_const/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();

        assertThrows(IllegalStateException.class, () -> parser.generateAST(tokenIterator));
    }

    @Test
    void test_const_declare() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_const_declare/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();

        assertThrows(IllegalStateException.class, () -> parser1.generateAST(tokenIterator));
    }

    @Test
    void test_If_Single() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_If_Single/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser1.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_If_Single/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_If_Else() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_If_Else/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser1.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_If_Else/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_readEnv() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_readEnv/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser1.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_readEnv/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_readInput() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_readInput/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser1.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_readInput/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_File() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_File/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_File/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    @Test
    void test_File2() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Token> tokens = objectMapper.readValue(
                new File("src/test/resources/test_File2/input.json"),
                new TypeReference<>() {
                }
        );

        Iterator<Token> tokenIterator = tokens.iterator();
        Iterator<ASTNode> actualAstIterator = parser1.generateAST(tokenIterator);
        List<ASTNode> actualAst = iteratorToList(actualAstIterator);

        List<ASTNode> expectedAst = objectMapper.readValue(
                new File("src/test/resources/test_File2/output.json"),
                new TypeReference<>() {
                }
        );

        assertEquals(expectedAst, actualAst);
    }

    private List<ASTNode> iteratorToList(Iterator<ASTNode> iterator){
        List<ASTNode> list = new ArrayList<>();
        while (iterator.hasNext()){
            list.add(iterator.next());
        }
        return list;
    }
}

