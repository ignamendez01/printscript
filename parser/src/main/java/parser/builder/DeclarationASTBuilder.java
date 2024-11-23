package parser.builder;

import ast.Declaration;
import ast.DeclarationAssignation;
import ast.interfaces.ASTNode;
import token.Token;

import java.util.List;

/**
 * El constructor de Declaration AST´s.
 */
public class DeclarationASTBuilder implements ASTBuilder<ASTNode> {
    private final String version;
    private final ValueASTBuilder valueASTBuilder;

    public DeclarationASTBuilder(String version) {
        this.version = version;
        this.valueASTBuilder = new ValueASTBuilder(version);
    }

    @Override
    public boolean verify(List<Token> statement) {
        if (statement.size() < 4) {
            return false;
        }
        if (!"KEYWORD".equals(statement.getFirst().getType())) {
            return false;
        }
        if (!"IDENTIFIER".equals(statement.get(1).getType())) {
            return false;
        }
        if (!"DECLARE".equals(statement.get(2).getType())) {
            return false;
        }
        return "TYPE".equals(statement.get(3).getType());
    }

    @Override
    public ASTNode build(List<Token> statement) {
        String keyword = statement.get(0).getValue();
        String identifier = statement.get(1).getValue();
        String type = statement.get(3).getValue();

        if (statement.size() > 4 && "ASSIGN".equals(statement.get(4).getType())) {
            return getDeclarationAssignation(statement, keyword, identifier, type);
        } else {
            return getDeclaration(keyword, identifier, type);
        }
    }

    private Declaration getDeclaration(String keyword, String identifier, String type) {
        if ("const".equals(keyword)) {
            if ("1.0".equals(version)) {
                throw new IllegalStateException("Invalid keyword 'const'");
            } else if ("1.1".equals(version)) {
                throw new IllegalStateException("const variable should have a value assigned");
            }
        }
        return new Declaration(identifier, type);
    }

    private DeclarationAssignation getDeclarationAssignation(List<Token> statement,
                                                             String keyword,
                                                             String identifier,
                                                             String type) {
        boolean isConst = "const".equals(keyword);
        if (isConst && "1.0".equals(version)) {
            throw new IllegalStateException("Invalid keyword 'const'");
        }

        List<Token> valueTokens = statement.subList(5, statement.size());

        return new DeclarationAssignation(
                new Declaration(identifier, type),
                valueASTBuilder.build(valueTokens),
                isConst
        );
    }
}


