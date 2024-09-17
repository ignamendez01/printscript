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
    private final String version;

    public Lexer(String version, Map<String, String> keywordMap, Map<Pattern, String> regexMap) {
        this.version = version;
        this.keywordMap = keywordMap;
        this.regexMap = regexMap;
    }

    public List<Token> makeTokens(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<Token> tokens = new ArrayList<>();
        Iterator<String> lineIterator = reader.lines().iterator();

        while (lineIterator.hasNext()) {
            String line = lineIterator.next().trim();
            Iterator<String> tokenIterator = TokenValueExtractor.extractTokenValues(version, line).iterator();

            while (tokenIterator.hasNext()) {
                String tokenValue = tokenIterator.next();
                tokens.add(mapToToken(tokenValue));
            }
        }
        return tokens;
    }

    private Token mapToToken(String tokenValue) {
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

