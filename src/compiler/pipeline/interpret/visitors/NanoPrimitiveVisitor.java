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

    public NanoPrimitiveVisitor() {
        this(new NanoPrimitiveDoubleVisitor(),
             new NanoPrimitiveIntegerVisitor(),
             new NanoPrimitiveStringVisitor());
    }

    public NanoPrimitiveVisitor(NanoPrimitiveDoubleVisitor doubleVisitor,
                                NanoPrimitiveIntegerVisitor intVisitor,
                                NanoPrimitiveStringVisitor stringVisitor) {

        logger = LoggerFactory.getLogger(NanoPrimitiveVisitor.class);
        this.doubleVisitor = doubleVisitor;
        this.intVisitor = intVisitor;
        this.stringVisitor = stringVisitor;
    }

    @Override
    public ASTNode visitPrimitive(@NotNull NanosyntaxParser.PrimitiveContext ctx) {
        logger.debug("Visiting primitive: {}", ctx.getText());
        ParseTree child = ctx.getChild(0);
        if (child instanceof IntPrimitiveContext) {
            return child.accept(intVisitor);
        } else if (child instanceof StringPrimitiveContext) {
            return child.accept(stringVisitor);
        } else if (child instanceof FloatPrimitiveContext) {
            return child.accept(doubleVisitor);
        } else {
            throw new IllegalStateException("Unexpected narrow primitive " +
                    "class " + child.getClass().getSimpleName());
        }
    }
}