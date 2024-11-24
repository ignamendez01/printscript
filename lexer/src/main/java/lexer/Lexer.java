package lexer;

import token.Token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * el lexer.
 */
public class Lexer {
    private final Map<String, String> keywordMap;
    private final Map<Pattern, String> regexMap;
    final String version;
    private final List<String> forbidden;
    int line = 1;
    int column = 1;

    public Lexer(String version, Map<String, String> keywordMap, Map<Pattern, String> regexMap) {
        this.version = version;
        this.keywordMap = keywordMap;
        this.regexMap = regexMap;
        this.forbidden = ForbiddenListFactory.getList(version);
    }

    public Iterator<Token> makeTokens(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Iterator<String> lineIterator = TokenValueExtractor.extractTokenValues(version, reader.lines().iterator()).iterator();

        return new TokenIterator(lineIterator, this);
    }

    public Token mapToToken(String tokenValue) {
        if (keywordMap.containsKey(tokenValue)) {
            Token token = new Token(keywordMap.get(tokenValue), tokenValue, line, column);
            column++;
            if (Objects.equals(tokenValue, ";") || Objects.equals(tokenValue, "{")){
                line++;
                column = 1;
            }
            return token;
        } else {
            for (Map.Entry<Pattern, String> entry : regexMap.entrySet()) {
                if (entry.getKey().matcher(tokenValue).matches()) {
                    return new Token(entry.getValue(), tokenValue, line, column);
                }
            }
            if (isValidVariableName(tokenValue) && !forbidden.contains(tokenValue)) {
                return new Token("IDENTIFIER", tokenValue, line, column);
            }else {
                throw new IllegalStateException("Unexpected value at line " + line + " ; column " + column);
            }
        }
    }

    private static boolean isValidVariableName(String s) {
        return s.matches("^[a-zA-Z_][a-zA-Z0-9_]*$");
    }

    private static class ForbiddenListFactory {
        public static List<String> getList(String version) {
            if (Objects.equals(version, "1.0")) {
                return Arrays.asList("const", "if", "else", "readEnv", "readInput");
            } else {
                return new ArrayList<>();
            }
        }
    }
}

class TokenIterator implements Iterator<Token> {
    private final Iterator<String> lineIterator;
    private final Lexer lexer;

    public TokenIterator(Iterator<String> lineIterator, Lexer lexer) {
        this.lineIterator = lineIterator;
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        return lineIterator != null && lineIterator.hasNext();
    }

    @Override
    public Token next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String tokenValue = lineIterator.next();
        return lexer.mapToToken(tokenValue);
    }
}


