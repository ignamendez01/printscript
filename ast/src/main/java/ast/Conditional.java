package ast;

import ast.interfaces.ASTNode;
import ast.interfaces.ValueNode;

import java.util.List;
import java.util.Objects;

public class Conditional implements ASTNode {
    private final ValueNode operator;
    private final List<ASTNode> trueBranch;
    private final List<ASTNode> falseBranch;

    public Conditional(ValueNode operator, List<ASTNode> trueBranch, List<ASTNode> falseBranch) {
        this.operator = operator;
        this.trueBranch = trueBranch;
        this.falseBranch = falseBranch;
    }

    public ValueNode getOperator() {
        return operator;
    }

    public List<ASTNode> getTrueBranch() {
        return trueBranch;
    }

    public List<ASTNode> getFalseBranch() {
        return falseBranch;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conditional that = (Conditional) o;
        return Objects.equals(operator, that.operator) &&
                Objects.equals(trueBranch, that.trueBranch) &&
                Objects.equals(falseBranch, that.falseBranch);
    }

    @Override
    public int hashCode() {
        int result = operator.hashCode();
        result = 31 * result + trueBranch.hashCode();
        result = 31 * result + (falseBranch != null ? falseBranch.hashCode() : 0);
        return result;
    }
}
