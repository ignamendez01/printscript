package lexer;

import token.Token;
import token.TokenCreator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Lexer {
    private final TokenCreator tokenCreator = new TokenCreator();
    private final String tokenFile;

    public Lexer(String tokenFile) {
        this.tokenFile = tokenFile;
    }

    public List<Token> makeTokens(String inputText) {
        List<Token> tokens = new ArrayList<>();
        List<List<String>> matrix = KeyWordMatrix.getMatrix(inputText);

        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                Token token = tokenCreator.createToken(matrix.get(i).get(j), i+1, j+1, tokenFile);
                tokens.add(token);

            }
        }

        return tokens;
    }

    public List<Token> makeTokens(InputStream fileInput) {
        BufferedReader br = new BufferedReader(new InputStreamReader(fileInput));
        String currentLine;
        try {
            currentLine = br.readLine();
        } catch (Exception e) {
            throw new RuntimeException("Error reading input", e);
        }
        if (currentLine == null) return new ArrayList<>();

        StringBuilder statement = new StringBuilder();
        try {
            while (currentLine != null) {
                String line = currentLine.trim();
                statement.append(line).append("\n");
                currentLine = br.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading statement", e);
        }

        return makeTokens(statement.toString());
    }

}