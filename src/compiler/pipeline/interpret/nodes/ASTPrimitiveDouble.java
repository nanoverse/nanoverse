/*
 * Copyright (c) 2015 David Bruce Borenstein and the Trustees
 * of Princeton University. All rights reserved.
 */

package compiler.pipeline.interpret.nodes;

import com.google.common.base.Strings;

/**
 * Created by dbborens on 3/5/15.
 */
public class ASTPrimitiveDouble extends ASTPrimitiveNode<Double> {
    public static final String IDENTIFIER = "AST_PRIMITIVE_DOUBLE";
    public ASTPrimitiveDouble(Double content) {
        super(content);
    }

    @Override
    public void astReport(StringBuilder builder, int indentLevel) {
        builder.append(Strings.repeat(" ", indentLevel));
        builder.append("double: " + getContent() + "\n");
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }
}
