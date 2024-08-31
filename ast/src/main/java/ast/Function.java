package ast;

import ast.interfaces.ValueNode;

public class Function implements ValueNode {
    private final String name;
    private final String value;

    public Function(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
