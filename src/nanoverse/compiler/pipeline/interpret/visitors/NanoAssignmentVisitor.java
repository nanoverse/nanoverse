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

package nanoverse.compiler.pipeline.interpret.visitors;

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser;
import nanoverse.compiler.pipeline.interpret.nodes.*;
import nanoverse.compiler.pipeline.interpret.visitors.helpers.NanoBlockHelper;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.*;

import java.util.stream.Stream;

import static nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.*;

/**
 * Created by dbborens on 4/22/15.
 */
public class NanoAssignmentVisitor extends AbstractNanoNodeVisitor {

    private final Logger logger;
    private final NanoSingletonVisitor singletonVisitor;
    private final NanoBlockVisitor blockVisitor;

    public NanoAssignmentVisitor(NanoBlockHelper blockHelper) {
        logger = LoggerFactory.getLogger(NanoAssignmentVisitor.class);
        singletonVisitor = new NanoSingletonVisitor(this);
        blockVisitor = new NanoBlockVisitor(blockHelper);
    }

    public NanoAssignmentVisitor(NanoSingletonVisitor singletonVisitor,
                                 NanoBlockVisitor blockVisitor) {

        logger = LoggerFactory.getLogger(NanoAssignmentVisitor.class);
        this.singletonVisitor = singletonVisitor;
        this.blockVisitor = blockVisitor;
    }

    @Override
    public ASTNode visitAssignment(@NotNull NanosyntaxParser.AssignmentContext ctx) {
        logger.debug("Visiting assignment: {}", ctx.getText());
        return byCase(ctx);
    }

    private ASTNode byCase(AssignmentContext ctx) {
        String id = getIdentifier(ctx);
        int n = ctx.getChildCount();

        // myRef {...}
        if (n == 2) {
            return blockCase(ctx, id);

            // myRef: myValue
        } else if (n == 3) {
            return singletonCase(ctx, id);

        } else {
            throw new IllegalStateException("Unexpected node count in AssignmentNode");
        }
    }

    private ASTNode blockCase(AssignmentContext ctx, String id) {
        logger.debug("Resolving assignment of block to {}", id);
        BlockContext child = (BlockContext) ctx.getChild(1);
        Stream<ASTNode> children = blockVisitor.getChildrenAsNodes(child);
        return new ASTNode(id, children);
    }

    private ASTNode singletonCase(AssignmentContext ctx, String id) {
        logger.debug("Resolving assignment of singleton to {}", id);
        ParseTree child = ctx.getChild(2);
        ASTNode value = child.accept(singletonVisitor);
        Stream<ASTNode> children = Stream.of(value);
        return new ASTNode(id, children);
    }

    private String getIdentifier(AssignmentContext ctx) {
        ParseTree idTree = ctx.getChild(0).getChild(0);
        verifyPayload(idTree, CommonToken.class);
        String identifier = idTree.getText();
        return identifier;
    }

}
