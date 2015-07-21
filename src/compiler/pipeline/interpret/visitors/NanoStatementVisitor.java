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

import static compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.AssignmentContext;

/**
 * Created by dbborens on 4/22/15.
 */
public class NanoStatementVisitor extends AbstractNanoNodeVisitor {
    private final Logger logger;
    private final NanoAssignmentVisitor assignmentVisitor;

    private final Class[] legalChildContexts = new Class[] {
            AssignmentContext.class,
    };

    public NanoStatementVisitor() {
        logger = LoggerFactory.getLogger(NanoStatementVisitor.class);
        NanoBlockVisitor blockVisitor = new NanoBlockVisitor(this);
        assignmentVisitor = new NanoAssignmentVisitor(blockVisitor);
    }

    public NanoStatementVisitor(NanoAssignmentVisitor assignmentVisitor) {
        logger = LoggerFactory.getLogger(NanoStatementVisitor.class);
        this.assignmentVisitor = assignmentVisitor;
    }

    @Override
    public ASTNode visitStatement(@NotNull NanosyntaxParser.StatementContext ctx) {
        logger.debug("Visiting statement: {}", ctx.getText());

        // Second child is a semicolon (checked by ANTLR -- ignored)
        ParseTree child = ctx.getChild(0);
        verifyPayload(child, legalChildContexts);

        return child.accept(assignmentVisitor);
    }
}
