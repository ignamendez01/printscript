package lexer;

import java.util.Objects;

public class LexerFactory {

    public static Lexer lexerVersion(String version) {
        if (Objects.equals(version, "1.0")){
            return new Lexer("token_types_1.0.txt");
        }else if (Objects.equals(version, "1.1")){
            return new Lexer("token_types_1.1.txt");
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }
}
