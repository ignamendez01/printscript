package parser.builder;

import ast.*;
import ast.interfaces.ASTNode;
import token.Token;

import java.util.List;

public class DeclarationASTBuilder implements ASTBuilder<ASTNode> {
    private final String version;
    private final ValueASTBuilder valueASTBuilder;

    public DeclarationASTBuilder(String version) {
        this.version = version;
        this.valueASTBuilder = new ValueASTBuilder(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        return statement.size() >= 4
                && "KEYWORD".equals(statement.get(0).getType())
                && "IDENTIFIER".equals(statement.get(1).getType())
                && "DECLARE".equals(statement.get(2).getType())
                && "TYPE".equals(statement.get(3).getType());
    }

    @Override
    public ASTNode build(List<Token> statement) {
        if (statement.size() > 4 && "ASSIGN".equals(statement.get(4).getType())) {
            return getDeclarationAssignation(statement);
        } else {
            return getDeclaration(statement);
        }
    }

    private Declaration getDeclaration(List<Token> statement) {
        String keyword = statement.get(0).getValue();
        if ("const".equals(keyword)) {
            switch (version){
                case "1.0" : throw new IllegalStateException("Invalid keyword 'const'");
                case "1.1": throw new IllegalStateException("const variable should have a value assigned");
            }
        }
        return new Declaration(statement.get(1).getValue(), statement.get(3).getValue());
    }

    private DeclarationAssignation getDeclarationAssignation(List<Token> statement) {
        boolean isConst = "const".equals(statement.get(0).getValue());
        if (isConst && "1.0".equals(version)) {
            throw new IllegalStateException("Invalid keyword 'const'");
        }
        return new DeclarationAssignation(
                new Declaration(statement.get(1).getValue(), statement.get(3).getValue()),
                valueASTBuilder.build(statement.subList(5, statement.size())),
                isConst
        );
    }
}

