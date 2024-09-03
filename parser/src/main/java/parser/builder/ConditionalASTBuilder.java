package parser.builder;

import ast.BooleanOperator;
import ast.Conditional;
import parser.Parser;
import token.Token;

import java.util.List;
import java.util.Objects;

public class ConditionalASTBuilder implements ASTBuilder<Conditional> {

    @Override
    public boolean verify(List<Token> statement) {
        if (!Objects.equals(statement.getFirst().getType(), "IF")) {
            return false;
        } else if (!Objects.equals(statement.get(1).getType(), "LPAR")) {
            return false;
        } else if (!Objects.equals(statement.get(2).getType(), "BOOLEAN")){
            return !Objects.equals(statement.get(2).getType(), "IDENTIFIER");
        }else if (!Objects.equals(statement.get(3).getType(), "RPAR")){
            return false;
        }else if (!Objects.equals(statement.get(4).getType(), "LKEY")){
            return false;
        }else return Objects.equals(statement.getLast().getType(), "RKEY");
    }

    @Override
    public Conditional build(List<Token> statement) {
        Parser parser = Parser.parserVersion("1.1");
        int elsePosition = findElse(statement);
        if (elsePosition != -1){
            return new Conditional(
                    new BooleanOperator(statement.get(2).getValue()),
                    parser.generateAST(statement.subList(5, elsePosition-1)),
                    parser.generateAST(statement.subList(elsePosition+2, statement.size()-1))
            );
        }else{
            return new Conditional(
                    new BooleanOperator(statement.get(2).getValue()),
                    parser.generateAST(statement.subList(5, statement.size()-1)),
                    null
            );
        }
    }

    private int findElse(List<Token> statement){
        int position = -1;
        for (int i = 0; i < statement.size(); i++) {
            if (statement.get(i).getType().equals("ELSE")) {
                position = i;
                break;
            }
        }
        return position;
    }
}
