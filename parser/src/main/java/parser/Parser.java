package parser;

import ast.interfaces.ASTNode;
import parser.builder.*;
import token.Token;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Parser {
    private final String version;
    private final List<ASTBuilder<? extends ASTNode>> astBuilders;

    public Parser(String version, List<ASTBuilder<? extends ASTNode>> astBuilders) {
        this.version = version;
        this.astBuilders = astBuilders;
    }

    public List<ASTNode> generateAST(Stream<Token> tokenStream) throws Exception {
        List<Token> tokenList = tokenStream.toList();
        List<ASTNode> astNodes = new ArrayList<>();
        List<Token> currentSegment = new ArrayList<>();

        for (int i = 0; i < tokenList.size(); i++) {
            Token token = tokenList.get(i);

            if (Objects.equals(token.getType(), "END")) {
                createTree(currentSegment, astNodes);
                currentSegment.clear();
            } else if (Objects.equals(version, "1.1") && Objects.equals(token.getType(), "IF")) {
                i = processIfBlock(tokenList, currentSegment, i);
                createTree(currentSegment, astNodes);
                currentSegment.clear();
            } else {
                currentSegment.add(token);
            }
        }

        // Procesar el último segmento si no está vacío
        if (!currentSegment.isEmpty()) {
            createTree(currentSegment, astNodes);
        }

        return astNodes;
    }

    private void createTree(List<Token> currentSegment, List<ASTNode> astNodes) throws Exception {
        boolean success = false;
        for (ASTBuilder<? extends ASTNode> builder : astBuilders) {
            if (builder.verify(currentSegment)) {
                astNodes.add(builder.build(currentSegment));
                success = true;
                break;
            }
        }
        if (!success) {
            throw new Exception("No builder available for the given token pattern");
        }
    }

    private int processIfBlock(List<Token> tokenList, List<Token> currentSegment, int i) {
        for (int j = i; j < tokenList.size(); j++) {
            Token token = tokenList.get(j);
            currentSegment.add(token);

            if (Objects.equals(token.getType(), "RKEY")) {
                i = j;
                break;
            }
        }

        if (i + 1 < tokenList.size()) {
            Token nextToken = tokenList.get(i + 1);
            if (Objects.equals(nextToken.getType(), "ELSE")) {
                currentSegment.add(nextToken);
                i++;

                for (int j = i + 1; j < tokenList.size(); j++) {
                    Token elseToken = tokenList.get(j);
                    currentSegment.add(elseToken);
                    if (Objects.equals(elseToken.getType(), "RKEY")) {
                        i = j;
                        break;
                    }
                }
            }
        }

        return i;
    }
}