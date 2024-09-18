package lexer;

import token.Token;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

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
        Iterator<String> lineIterator = reader.lines().iterator();

        return new TokenIterator(lineIterator, this);
    }

    public Token mapToToken(String tokenValue) {
        if (keywordMap.containsKey(tokenValue)) {
            return new Token(keywordMap.get(tokenValue), tokenValue);
        } else {
            for (Map.Entry<Pattern, String> entry : regexMap.entrySet()) {
                if (entry.getKey().matcher(tokenValue).matches()) {
                    return new Token(entry.getValue(), tokenValue);
                }
            }
            return isValidVariableName(tokenValue) ? new Token("IDENTIFIER", tokenValue) : new Token("UNKNOWN", tokenValue);
        }
    }

    private static boolean isValidVariableName(String s) {
        return s.matches("^[a-zA-Z_][a-zA-Z0-9_]*$");
    }
}

class TokenIterator implements Iterator<Token> {
    private final Iterator<String> lineIterator;
    private Iterator<String> currentTokenIterator;
    private final Lexer lexer;

    public TokenIterator(Iterator<String> lineIterator, Lexer lexer) {
        this.lineIterator = lineIterator;
        this.lexer = lexer;
    }

    @Override
    public boolean hasNext() {
        while ((currentTokenIterator == null || !currentTokenIterator.hasNext()) && lineIterator.hasNext()) {
            currentTokenIterator = TokenValueExtractor.extractTokenValues(lexer.version, lineIterator).iterator();
        }
        return currentTokenIterator != null && currentTokenIterator.hasNext();
    }

    @Override
    public Token next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        String tokenValue = currentTokenIterator.next();
        return lexer.mapToToken(tokenValue);
    }
}


