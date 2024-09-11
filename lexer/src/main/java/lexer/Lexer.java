package lexer;

import token.Token;
import token.TokenCreator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Stream;

public class Lexer {
    private final TokenCreator tokenCreator;
    private final String version;

    public Lexer(String version, String tokenFile) {
        this.version = version;
        this.tokenCreator = new TokenCreator(tokenFile);
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
            Token token = tokenCreator.createToken(tokenValue);
            tokens.add(token);
        }
    }
}