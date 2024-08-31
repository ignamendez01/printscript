package ast;

import ast.interfaces.ValueNode;

public class BooleanOperator implements ValueNode {
    private final String value;

    public BooleanOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}