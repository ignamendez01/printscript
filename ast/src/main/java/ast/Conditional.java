package ast;

import ast.interfaces.ASTNode;
import ast.interfaces.BooleanOperator;

import java.util.ArrayList;

public class Conditional implements ASTNode {
    private final BooleanOperator operator;
    private final ArrayList<ASTNode> trueBranch;
    private final ArrayList<ASTNode> falseBranch;

    public Conditional(BooleanOperator operator, ArrayList<ASTNode> trueBranch, ArrayList<ASTNode> falseBranch) {
        this.operator = operator;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public BooleanOperator getOperator() {
        return operator;
    }

    public ArrayList<ASTNode> getTrueBranch() {
        return trueBranch;
    }

    public ArrayList<ASTNode> getFalseBranch() {
        return falseBranch;
    }
}
