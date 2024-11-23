package parser.builder;

import ast.interfaces.ASTNode;
import token.Token;

import java.util.List;

/**
 * la interfaz de constructores de AST´s.
 */
public interface ASTBuilder<T extends ASTNode> {
    // Method to verify if this builder can build the AST for the given tokens
    boolean verify(List<Token> statement);

    // Method to actually build the AST for the given tokens
    T build(List<Token> statement) throws Exception;

}

