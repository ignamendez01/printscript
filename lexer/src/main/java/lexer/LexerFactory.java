package lexer;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

public class LexerFactory {

    public static Lexer lexerVersion(String version) {
        if (Objects.equals(version, "1.0")){
            return new Lexer("1.0", Map.ofEntries(
                    Map.entry("let", "KEYWORD"),
                    Map.entry(":", "DECLARE"),
                    Map.entry("=", "ASSIGN"),
                    Map.entry("println", "METHOD"),
                    Map.entry("(", "LPAR"),
                    Map.entry(")", "RPAR"),
                    Map.entry(";", "END")
            ), Map.of(
                    Pattern.compile("\"[^\"]*\"|'[^']*'"), "STRING",
                    Pattern.compile("-?\\d+(\\.\\d+)?"), "NUMBER",
                    Pattern.compile("[+\\-*/]"), "OPERATOR",
                    Pattern.compile("string|number"), "TYPE"
            ));
        }else if (Objects.equals(version, "1.1")){
            return new Lexer("1.1", Map.ofEntries(
                    Map.entry(":", "DECLARE"),
                    Map.entry("=", "ASSIGN"),
                    Map.entry("println", "METHOD"),
                    Map.entry("if", "IF"),
                    Map.entry("else", "ELSE"),
                    Map.entry("(", "LPAR"),
                    Map.entry(")", "RPAR"),
                    Map.entry("{", "LKEY"),
                    Map.entry("}", "RKEY"),
                    Map.entry(";", "END")
            ), Map.of(
                    Pattern.compile("let|const"), "KEYWORD",
                    Pattern.compile("\"[^\"]*\"|'[^']*'"), "STRING",
                    Pattern.compile("-?\\d+(\\.\\d+)?"), "NUMBER",
                    Pattern.compile("true|false"), "BOOLEAN",
                    Pattern.compile("[+\\-*/]"), "OPERATOR",
                    Pattern.compile("string|number|boolean"), "TYPE",
                    Pattern.compile("readEnv|readInput"), "FUNCTION"
            ));
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }
}
