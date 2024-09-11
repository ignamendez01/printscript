package lexer;

import token.Token;
import token.TokenCreator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Lexer {
    private final TokenCreator tokenCreator;
    private final String version;

    public Lexer(String version, String tokenFile) {
        this.version = version;
        this.tokenCreator = new TokenCreator(tokenFile);
    }

    public List<Token> makeTokens(String inputText) {
        List<Token> tokens = new ArrayList<>();
        List<String> tokenValues = TokenValueExtractor.extractTokenValues(version, inputText);

        createTokenList(tokenValues, tokens);

        return tokens;
    }

    private void createTokenList(List<String> tokenValues, List<Token> tokens) {
        int row = 1;
        int column = 1;
        for (String tokenValue : tokenValues) {
            Token token = tokenCreator.createToken(tokenValue, row, column);
            if(Objects.equals(tokenValue, ";")){
                row++;
                column = 1;
            }else{
                column++;
            }
            tokens.add(token);
        }
    }

    public List<Token> makeTokens(InputStream fileInput) {
        List<Token> allTokens = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInput));
        String currentLine = readLine(br);

        while (currentLine != null) {
            String line = currentLine.trim();
            List<Token> lineTokens = makeTokens(line);
            allTokens.addAll(lineTokens);
            currentLine = readLine(br);
        }

        return allTokens;
    }

    private static String readLine(BufferedReader br) {
        try {
            return br.readLine();
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
    }

}