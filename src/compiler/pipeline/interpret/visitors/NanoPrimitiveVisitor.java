/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein and the
 * Trustees of Princeton University.
 *
 * This file is part of the Nanoverse simulation framework
 * (patent pending).
 *
 * This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU Affero General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Affero General
 * Public License along with this program.  If not, see
 * <http://www.gnu.org/licenses/>.
 */

package compiler.pipeline.interpret.visitors;

import compiler.pipeline.interpret.nanosyntax.NanosyntaxParser;
import compiler.pipeline.interpret.nodes.ASTNode;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.*;

import static compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.*;

/**
 * Created by dbborens on 4/22/15.
 */
public class NanoPrimitiveVisitor extends AbstractNanoNodeVisitor {
    private final Logger logger;
    private final NanoPrimitiveDoubleVisitor doubleVisitor;
    private final NanoPrimitiveIntegerVisitor intVisitor;
    private final NanoPrimitiveStringVisitor stringVisitor;
    private final NanoPrimitiveBooleanVisitor booleanVisitor;

    public NanoPrimitiveVisitor() {
        this(new NanoPrimitiveDoubleVisitor(),
             new NanoPrimitiveIntegerVisitor(),
             new NanoPrimitiveStringVisitor(),
             new NanoPrimitiveBooleanVisitor());
    }

    public NanoPrimitiveVisitor(NanoPrimitiveDoubleVisitor doubleVisitor,
                                NanoPrimitiveIntegerVisitor intVisitor,
                                NanoPrimitiveStringVisitor stringVisitor,
                                NanoPrimitiveBooleanVisitor booleanVisitor) {

        logger = LoggerFactory.getLogger(NanoPrimitiveVisitor.class);
        this.doubleVisitor = doubleVisitor;
        this.intVisitor = intVisitor;
        this.stringVisitor = stringVisitor;
        this.booleanVisitor = booleanVisitor;
    }

    @Override
    public ASTNode visitPrimitive(@NotNull NanosyntaxParser.PrimitiveContext ctx) {
        ParseTree child = ctx.getChild(0);
        if (child instanceof IntPrimitiveContext) {
            logDebug("an Integer", ctx, child);
            return child.accept(intVisitor);
        } else if (child instanceof StringPrimitiveContext) {
            logDebug("a String", ctx, child);
            return child.accept(stringVisitor);
        } else if (child instanceof FloatPrimitiveContext) {
            logDebug("a Double", ctx, child);
            return child.accept(doubleVisitor);
        } else if (child instanceof BoolPrimitiveContext) {
            logDebug("a Boolean", ctx, child);
            return child.accept(booleanVisitor);
        } else {
            throw new IllegalStateException("Unexpected narrow primitive " +
                    "class " + child.getClass().getSimpleName());
        }
    }

    private void logDebug(String type, ParseTree parent, ParseTree child) {
        logger.debug("Primitive literal {} is identified as " +
                "{}. Recurring on child {}.",
            parent.getText(),
            type,
            child.getText());
    }
}