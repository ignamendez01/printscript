package lexer;

import token.Token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
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

    public Lexer(String version, Map<String, String> keywordMap, Map<Pattern, String> regexMap) {
        this.version = version;
        this.keywordMap = keywordMap;
        this.regexMap = regexMap;
    }

    public Iterator<Token> makeTokens(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Iterator<String> lineIterator = TokenValueExtractor.extractTokenValues(reader.lines().iterator()).iterator();

        return new TokenIterator(lineIterator, this, version);
    }

    public Token mapToToken(String tokenValue, int line, int column) {
        if (keywordMap.containsKey(tokenValue)) {
            return new Token(keywordMap.get(tokenValue), tokenValue, line, column);
        } else {
            for (Map.Entry<Pattern, String> entry : regexMap.entrySet()) {
                if (entry.getKey().matcher(tokenValue).matches()) {
                    return new Token(entry.getValue(), tokenValue, line, column);
                }
            }
            if (isValidVariableName(tokenValue)) {
                return new Token("IDENTIFIER", tokenValue, line, column);
            }else {
                throw new IllegalStateException("Unexpected value at line " + line + " ; column " + column);
            }
        }
    }

    private static boolean isValidVariableName(String s) {
        return s.matches("^[a-zA-Z_][a-zA-Z0-9_]*$");
    }
}

class TokenIterator implements Iterator<Token> {
    private final Iterator<String> lineIterator;
    private final Lexer lexer;
    private final String version;
    private int line = 1;
    private int column = 1;

    public TokenIterator(Iterator<String> lineIterator, Lexer lexer, String version) {
        this.lineIterator = lineIterator;
        this.lexer = lexer;
        this.version = version;
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
        if (Objects.equals(version, "1.0")){
            Token token = lexer.mapToToken(tokenValue, line, column);
            column++;
            if (Objects.equals(tokenValue, ";")){
                line++;
                column = 1;
            }
            return token;
        }else {
            if (Objects.equals(tokenValue, "else")){
                line--;
                column = 2;
            }
            Token token = lexer.mapToToken(tokenValue, line, column);
            column++;
            if (Objects.equals(tokenValue, ";") || Objects.equals(tokenValue, "{") || Objects.equals(tokenValue, "}")) {
                line++;
                column = 1;
            }
            return token;
        }
    }
}


