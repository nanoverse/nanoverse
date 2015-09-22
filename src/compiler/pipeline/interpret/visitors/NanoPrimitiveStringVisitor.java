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
import compiler.pipeline.interpret.nodes.*;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.*;

import java.util.stream.Stream;

/**
 * Created by dbborens on 2/15/15.
 */
public class NanoPrimitiveStringVisitor extends AbstractNanoNodeVisitor {

    private final Logger logger = LoggerFactory.getLogger(NanoPrimitiveIntegerVisitor.class);
    public static final String IDENTIFIER = "ConstantString";

    @Override
    public ASTNode visitStringPrimitive(@NotNull NanosyntaxParser.StringPrimitiveContext ctx) {
        if (ctx.getChildCount() != 1) {
            throw new IllegalStateException("Malformed primitive");
        }

        ParseTree child = ctx.getChild(0);
        verifyPayload(child, CommonToken.class);

        String valueText = child.getText();
        valueText = valueText.replaceAll("^\"|\"$", "");
        ASTNode valueNode = new ASTNode(valueText, Stream.empty());
        Stream<ASTNode> children = Stream.of(valueNode);
        ASTNode container = new ASTNode(IDENTIFIER, children);
        logger.debug("Translated literal \"{}\" as a String primitive.", valueText);
        return container;
    }
}
