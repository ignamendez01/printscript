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

    public List<ASTNode> generateAST(Stream<Token> tokenStream) {
        List<ASTNode> astNodes = new ArrayList<>();
        List<Token> currentSegment = new ArrayList<>();
        Iterator<Token> tokenIterator = tokenStream.iterator();

        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();

            if (Objects.equals(token.getType(), "END")) {
                createTree(currentSegment, astNodes);
                currentSegment.clear();
            } else if (Objects.equals(version, "1.1") && Objects.equals(token.getType(), "IF")) {
                currentSegment.add(token);
                Token nextToken = processIfBlock(tokenIterator, currentSegment);
                createTree(currentSegment, astNodes);
                currentSegment.clear();
                if (nextToken != null) {
                    currentSegment.add(nextToken);
                }
            } else {
                currentSegment.add(token);
            }
        }

        return astNodes;
    }

    private void createTree(List<Token> currentSegment, List<ASTNode> astNodes) {
        for (ASTBuilder<? extends ASTNode> builder : astBuilders) {
            if (builder.verify(currentSegment)) {
                astNodes.add(builder.build(currentSegment));
                break;
            }
        }
    }

    private Token processIfBlock(Iterator<Token> tokenIterator, List<Token> currentSegment) {
        while (tokenIterator.hasNext()) {
            Token token = tokenIterator.next();
            currentSegment.add(token);
            if (Objects.equals(token.getType(), "RKEY")) {
                break;
            }
        }

        if (tokenIterator.hasNext()) {
            Token nextToken = tokenIterator.next();
            if (Objects.equals(nextToken.getType(), "ELSE")) {
                currentSegment.add(nextToken);

                while (tokenIterator.hasNext()) {
                    Token elseToken = tokenIterator.next();
                    currentSegment.add(elseToken);
                    if (Objects.equals(elseToken.getType(), "RKEY")) {
                        break;
                    }
                }
                return null;
            }else{
                return nextToken;
            }
        }else{
            return null;
        }
    }
}