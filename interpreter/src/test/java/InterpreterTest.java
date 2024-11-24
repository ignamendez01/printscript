import ast.interfaces.ASTNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import interpreter.Administrator;
import interpreter.Interpreter;
import interpreter.InterpreterFactory;
import interpreter.response.ErrorResponse;
import interpreter.response.InterpreterResponse;
import interpreter.response.SuccessResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

/**
 * el test del intrerpreter.
 */
public class InterpreterTest {
    private final Interpreter interpreter = InterpreterFactory.interpreterVersion(new Administrator(), "1.0");
    private final Interpreter interpreter1 = InterpreterFactory.interpreterVersion(new Administrator(), "1.1");

    @Test
    public void test_Sum_String_Number() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Sum_String_Number.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello5", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Sum_Identifier_String() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Sum_Identifier_String.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("1Hello", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Multiply_Numbers() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Multiply_Numbers.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("25", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Sum_Identifiers() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Sum_Identifiers.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("10", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Subtract_Numbers() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Subtract_Numbers.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("5", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Divide_Numbers() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Divide_Numbers.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("2", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_should_concatenate_two_strings() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_should_concatenate_two_strings.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello World", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Assign_Declared_Value() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Assign_Declared_Value.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_Operation() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_Operation.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("54", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_true() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_if_true.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("12", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_false() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_if_false.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_one_tree_true() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_if_one_tree_true.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("12", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_one_tree_false() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_if_one_tree_false.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_boolean_variable_true() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_if_boolean_variable_true.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("12", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_if_boolean_variable_false() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_if_boolean_variable_false.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_readInput() throws Exception {
        String input = "14";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_readinput.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);

        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Insert a number: ", interpreter1.getAdmin().getPrintedElements().poll());
        assertEquals("14", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_readEnv() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_readEnv.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Ignacio", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_File() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_File.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);

        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Hello 20", interpreter.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_File2() throws Exception {
        String input = "5";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_File2.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);

        assertInstanceOf(SuccessResponse.class, result);
        assertEquals("Value to multiply", interpreter1.getAdmin().getPrintedElements().poll());
        assertEquals("Value 25", interpreter1.getAdmin().getPrintedElements().poll());
    }

    @Test
    public void test_declare_already_declared() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_declare_already_declared.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter.interpretAST(astIterator);
        assertInstanceOf(ErrorResponse.class, result);
        assertEquals("Variable result already declared", ((ErrorResponse) result).message());
    }

    @Test
    public void test_edit_const() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_edit_const.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(ErrorResponse.class, result);
        assertEquals("Variable result is const", ((ErrorResponse) result).message());
    }

    @Test
    public void test_mismatch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_mismatch.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(ErrorResponse.class, result);
        assertEquals("Type mismatch in variable result assignment", ((ErrorResponse) result).message());
    }

    @Test
    public void test_edit_mismatch() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_edit_mismatch.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(ErrorResponse.class, result);
        assertEquals("Variable result is not type String", ((ErrorResponse) result).message());
    }

    @Test
    public void test_not_declared_variable() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_not_declared_variable.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);
        assertInstanceOf(ErrorResponse.class, result);
        assertEquals("Variable result not declared", ((ErrorResponse) result).message());
    }

    @Test
    public void test_unknown_env_variable() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ASTNode> asts = objectMapper.readValue(
                new File("src/test/resources/test_unknown_env_variable.json"),
                new TypeReference<>() {
                }
        );

        Iterator<ASTNode> astIterator = asts.iterator();
        InterpreterResponse result = interpreter1.interpretAST(astIterator);

        assertInstanceOf(ErrorResponse.class, result);
        assertEquals("Environment variable MAESTRO does not exist", ((ErrorResponse) result).message());
    }

}
