package parser.builder;

import ast.BooleanOperator;
import ast.Conditional;
import ast.IdentifierOperator;
import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;
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
        } else if (!Objects.equals(statement.get(2).getType(), "BOOLEAN") && !Objects.equals(statement.get(2).getType(), "IDENTIFIER")){
            return false;
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

        ValueNode operator;
        if (Objects.equals(statement.get(2).getType(), "BOOLEAN")) {
            operator = new BooleanOperator(transformBoolean(statement.get(2).getValue()));
        } else {
            operator = new IdentifierOperator(statement.get(2).getValue());
        }

        List<ASTNode> trueBranch = parser.generateAST(statement.subList(5, elsePosition != -1 ? elsePosition - 1 : statement.size() - 1));
        List<ASTNode> falseBranch = elsePosition != -1
                ? parser.generateAST(statement.subList(elsePosition + 2, statement.size() - 1))
                : null;

        return new Conditional(operator, trueBranch, falseBranch);
    }

    private boolean transformBoolean(String value) {
        return Objects.equals(value, "true");
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
