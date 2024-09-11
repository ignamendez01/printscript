package lexer;

import java.util.Map;
import java.util.Objects;

public class LexerFactory {

    public static Lexer lexerVersion(String version) {
        if (Objects.equals(version, "1.0")){
            return new Lexer("1.0", Map.ofEntries(
                    Map.entry("let", "KEYWORD"),
                    Map.entry(":", "DECLARE"),
                    Map.entry("string|number", "TYPE"),
                    Map.entry("=", "ASSIGN"),
                    Map.entry("\"[^\"]*\"|'[^']*'", "STRING"),
                    Map.entry("-?\\d+(\\.\\d+)?", "NUMBER"),
                    Map.entry("\\+|\\-|\\*|/", "OPERATOR"),
                    Map.entry("println", "METHOD"),
                    Map.entry("\\(", "LPAR"),
                    Map.entry("\\)", "RPAR"),
                    Map.entry(";", "END")
            ));
        }else if (Objects.equals(version, "1.1")){
            return new Lexer("1.1", Map.ofEntries(
                    Map.entry("let|const", "KEYWORD"),
                    Map.entry(":", "DECLARE"),
                    Map.entry("string|number|boolean", "TYPE"),
                    Map.entry("=", "ASSIGN"),
                    Map.entry("\"[^\"]*\"|'[^']*'", "STRING"),
                    Map.entry("-?\\d+(\\.\\d+)?", "NUMBER"),
                    Map.entry("true|false", "BOOLEAN"),
                    Map.entry("\\+|\\-|\\*|/", "OPERATOR"),
                    Map.entry("println", "METHOD"),
                    Map.entry("readEnv|readInput", "FUNCTION"),
                    Map.entry("if", "IF"),
                    Map.entry("else", "ELSE"),
                    Map.entry("\\(", "LPAR"),
                    Map.entry("\\)", "RPAR"),
                    Map.entry("\\{", "LKEY"),
                    Map.entry("\\}", "RKEY"),
                    Map.entry(";", "END")
            ));
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }
}
