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


    public static Parser parserVersion(String version){
        if (Objects.equals(version, "1.0")){
            return new Parser("1.0", List.of(
                    new AssignationASTBuilder(),
                    new DeclarationASTBuilder("1.0"),
                    new MethodASTBuilder()
            ));
        }else if (Objects.equals(version, "1.1")){
            return new Parser("1.1", List.of(
                    new AssignationASTBuilder(),
                    new DeclarationASTBuilder("1.1"),
                    new MethodASTBuilder(),
                    new ConditionalASTBuilder()
            ));
        }else{
            throw new IllegalStateException("Unexpected value: " + version);
        }
    }

    public List<ASTNode> generateAST(List<Token> tokens) {
        List<ASTNode> astNodes = new ArrayList<>();
        List<List<Token>> tokenMatrix = generateMatrix(tokens);
        for (List<Token> list : tokenMatrix) {
            for (ASTBuilder<? extends ASTNode> builder : astBuilders) {
                if (builder.verify(list)) {
                    astNodes.add(builder.build(list));
                    break;
                }
            }
        }
        return astNodes;
    }

    private List<List<Token>> generateMatrix(List<Token> tokens) {
        List<List<Token>> result = new ArrayList<>();
        List<Token> currentSegment = new ArrayList<>();

        if (tokens.isEmpty()){
            return result;
        }

        for (int i = 0; i < tokens.size(); i++) {
            Token token = tokens.get(i);
            if (Objects.equals(token.getType(), "END")) {
                if (!currentSegment.isEmpty()) {
                    result.add(new ArrayList<>(currentSegment));
                    currentSegment.clear();
                }
            }else if(Objects.equals(version, "1.1") && Objects.equals(token.getType(), "IF")) {
                i = IfList(tokens, i, currentSegment, result);
            }else{
                currentSegment.add(token);
            }
        }

        if (!currentSegment.isEmpty()) {
            result.add(currentSegment);
        }

        return result;
    }

    private static int IfList(List<Token> tokens, int i, List<Token> currentSegment, List<List<Token>> result) {
        while (!Objects.equals(tokens.get(i).getType(), "RKEY")){
            currentSegment.add(tokens.get(i));
            i++;
        }
        currentSegment.add(tokens.get(i));
        i++;
        if (Objects.equals(tokens.get(i).getType(), "ELSE")){
            while (!Objects.equals(tokens.get(i).getType(), "RKEY")){
                currentSegment.add(tokens.get(i));
                i++;
            }
            currentSegment.add(tokens.get(i));
            i++;
        }
        result.add(new ArrayList<>(currentSegment));
        currentSegment.clear();
        return i;
    }
}
