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

package nanoverse.compiler.pipeline.interpret.visitors.helpers;

import nanoverse.compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.StatementContext;
import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.interpret.visitors.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.*;

import java.util.stream.*;

/**
 * Created by dbborens on 4/30/15.
 */
public class NanoBlockHelper extends AbstractNanoNodeVisitor {

    private final NanoStatementVisitor statementVisitor;
    private final Logger logger;

    public NanoBlockHelper() {
        logger = LoggerFactory.getLogger(NanoBlockHelper.class);
        statementVisitor = new NanoStatementVisitor(this);
    }

    public NanoBlockHelper(NanoStatementVisitor statementVisitor) {
        this.statementVisitor = statementVisitor;
        logger = LoggerFactory.getLogger(NanoBlockHelper.class);
    }

    public Stream<ASTNode> doVisit(ParserRuleContext ctx, int start, int end) {
        logger.debug("Visiting block: {}", ctx.getText());

        return IntStream.range(start, end)
            .mapToObj(ctx::getChild)
            .map(this::verifyAndAccept);
    }

    private ASTNode verifyAndAccept(ParseTree child) {
        verifyPayload(child, StatementContext.class);
        ASTNode ret = child.accept(statementVisitor);
        return ret;
    }
}
