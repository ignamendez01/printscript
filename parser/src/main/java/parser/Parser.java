package parser;

import ast.interfaces.ASTNode;
import parser.builder.*;
import token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    private final List<ASTBuilder<? extends ASTNode>> astBuilders =
            List.of(
                    new AssignationASTBuilder(),
                    new DeclarationASTBuilder(),
                    new MethodASTBuilder(),
                    new ValueASTBuilder()
            );

    // The list of tokens is of a single statement
    public List<ASTNode> generateAST(List<Token> tokens) {
        List<ASTNode> astNodes = new ArrayList<>();
        List<List<Token>> tokenMatrix = generateMatrix(tokens);
        for (List<Token> list : tokenMatrix) {
            for (ASTBuilder<? extends ASTNode> builder : astBuilders) {
                if (builder.verify(list)) {
                    astNodes.add(builder.build(list));
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

        for (Token token : tokens) {
            if (Objects.equals(token.getType(), "END")) {
                if (!currentSegment.isEmpty()) {
                    result.add(new ArrayList<>(currentSegment));
                    currentSegment.clear();
                }
            } else {
                currentSegment.add(token);
            }
        }

        if (!currentSegment.isEmpty()) {
            result.add(currentSegment);
        }

        return result;
    }
}
