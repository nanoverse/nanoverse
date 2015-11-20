/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.compiler.pipeline.interpret.visitors;

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.helpers.NanoBlockHelper;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.slf4j.*;

import java.util.stream.Stream;

/**
 * Created by dbborens on 4/21/15.
 */
public class NanoRootVisitor extends AbstractNanoNodeVisitor {

    public static final String IDENTIFIER = "root";
    private final Logger logger;
    private final NanoBlockHelper helper;

    public NanoRootVisitor() {
        logger = LoggerFactory.getLogger(NanoRootVisitor.class);
        helper = new NanoBlockHelper();
    }

    public NanoRootVisitor(NanoBlockHelper helper) {
        this.helper = helper;
        logger = LoggerFactory.getLogger(NanoRootVisitor.class);
    }

    public ASTNode visitRoot(@NotNull NanosyntaxParser.RootContext ctx) {
        Token token = ctx.getStart();
        int lineNumber = token.getLine();
        logger.debug("Visiting root with {} children. Text is \"{}\" and line number is {}.",
                ctx.getChildCount(), token.getText(), lineNumber);
        Stream<ASTNode> children = helper.doVisit(ctx, 0, ctx.getChildCount());
        ASTNode ret = new ASTNode(IDENTIFIER, children, lineNumber);
        return ret;
    }
}
