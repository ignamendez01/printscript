package lexer;

import token.Token;
import token.TokenCreator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Lexer {
    private final Map<String, String> keywordMap;
    private final Map<Pattern, String> regexMap;
    private final String version;

    public Lexer(String version, Map<String, String> keywordMap, Map<Pattern, String> regexMap) {
        this.version = version;
        this.keywordMap = keywordMap;
        this.regexMap = regexMap;
    }

    public Stream<Token> makeTokens(InputStream fileInput) {
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInput));
        return br.lines()
                .flatMap(line -> makeTokensFromString(line.trim()).stream());
    }

    private List<Token> makeTokensFromString(String inputText) {
        List<Token> tokens = new ArrayList<>();
        List<String> tokenValues = TokenValueExtractor.extractTokenValues(version, inputText);
        createTokenList(tokenValues, tokens);
        return tokens;
    }

    private void createTokenList(List<String> tokenValues, List<Token> tokens) {
        for (String tokenValue : tokenValues) {
            if (keywordMap.containsKey(tokenValue)) {
                tokens.add(new Token(keywordMap.get(tokenValue), tokenValue));
            } else {
                boolean matched = false;

                for (Map.Entry<Pattern, String> entry : regexMap.entrySet()) {
                    if (entry.getKey().matcher(tokenValue).matches()) {
                        tokens.add(new Token(entry.getValue(), tokenValue));
                        matched = true;
                        break;
                    }
                }

                if (!matched) {
                    if (isValidVariableName(tokenValue)) {
                        tokens.add(new Token("IDENTIFIER", tokenValue));
                    } else {
                        tokens.add(new Token("UNKNOWN", tokenValue));
                    }
                }
            }
        }
    }

    private static boolean isValidVariableName(String s) {
        String variableNamePattern = "^[a-zA-Z_][a-zA-Z0-9_]*$";
        Pattern pattern = Pattern.compile(variableNamePattern);
        return pattern.matcher(s).matches();
    }
}