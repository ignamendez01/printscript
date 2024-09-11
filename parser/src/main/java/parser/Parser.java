package parser;

import ast.interfaces.ASTNode;
import parser.builder.*;
import token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    private final String version;
    private final List<ASTBuilder<? extends ASTNode>> astBuilders;

    public Parser(String version, List<ASTBuilder<? extends ASTNode>> astBuilders) {
        this.version = version;
        this.astBuilders = astBuilders;
    }

    public List<ASTNode> generateAST(List<Token> tokens) {
        List<ASTNode> astNodes = new ArrayList<>();
        List<Token> currentSegment = new ArrayList<>();

        if (tokens.isEmpty()){
            return astNodes;
        }

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (Objects.equals(token.getType(), "END")) {
                createTree(currentSegment, astNodes);
                currentSegment.clear();
            }else if(Objects.equals(version, "1.1") && Objects.equals(token.getType(), "IF")) {
                i = IfList(tokens, i, currentSegment);
                createTree(currentSegment, astNodes);
                currentSegment.clear();
            }else{
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

    private static int IfList(List<Token> tokens, int i, List<Token> currentSegment) {
        while (!Objects.equals(tokens.get(i).getType(), "RKEY")){
            currentSegment.add(tokens.get(i));
            i++;
        }
        currentSegment.add(tokens.get(i));
        if (Objects.equals(tokens.get(i+1).getType(), "ELSE")){
            i++;
            while (!Objects.equals(tokens.get(i).getType(), "RKEY")){
                currentSegment.add(tokens.get(i));
                i++;
            }
            currentSegment.add(tokens.get(i));
        }
        return i;
    }
}
