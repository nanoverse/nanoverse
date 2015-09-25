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
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.helpers.NanoBlockHelper;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.*;

import java.util.stream.Stream;

/**
 * Created by dbborens on 4/22/15.
 */
public class NanoBlockVisitor extends AbstractNanoNodeVisitor {

    private final NanoBlockHelper helper;
    private final Logger logger;

    public NanoBlockVisitor() {
        logger = LoggerFactory.getLogger(NanoBlockVisitor.class);
        helper = new NanoBlockHelper();
    }

    public NanoBlockVisitor(NanoBlockHelper helper) {
        logger = LoggerFactory.getLogger(NanoBlockVisitor.class);
        this.helper = helper;
    }

    public Stream<ASTNode> getChildrenAsNodes(@NotNull NanosyntaxParser.BlockContext ctx) {
        logger.debug("Visiting block with {} children", ctx.getChildCount());
        Stream<ASTNode> children = helper.doVisit(ctx, 1, ctx.getChildCount() - 1);
        return children;
    }
}
