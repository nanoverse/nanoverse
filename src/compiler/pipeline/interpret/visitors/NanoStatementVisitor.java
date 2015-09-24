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
import compiler.pipeline.interpret.nanosyntax.NanosyntaxParser.*;
import compiler.pipeline.interpret.nodes.ASTNode;
import compiler.pipeline.interpret.visitors.helpers.NanoBlockHelper;
import compiler.pipeline.translate.visitors.PrimitiveVisitor;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.*;

/**
 * Created by dbborens on 4/22/15.
 */
public class NanoStatementVisitor extends AbstractNanoNodeVisitor {
    private final Logger logger;
    private final NanoAssignmentVisitor assignmentVisitor;
    private final NanoPrimitiveVisitor primitiveVisitor;
    private final NanoStandaloneIdVisitor standaloneVisitor;


    public NanoStatementVisitor(NanoBlockHelper blockHelper) {
        logger = LoggerFactory.getLogger(NanoStatementVisitor.class);

        assignmentVisitor = new NanoAssignmentVisitor(blockHelper);
        primitiveVisitor = new NanoPrimitiveVisitor();
        standaloneVisitor = new NanoStandaloneIdVisitor();
    }

    public NanoStatementVisitor( NanoStandaloneIdVisitor standaloneVisitor,
                                 NanoPrimitiveVisitor primitiveVisitor,
                                 NanoAssignmentVisitor assignmentVisitor) {
        logger = LoggerFactory.getLogger(NanoStatementVisitor.class);

        this.assignmentVisitor = assignmentVisitor;
        this.primitiveVisitor = primitiveVisitor;
        this.standaloneVisitor = standaloneVisitor;
    }

    @Override
    public ASTNode visitStatement(@NotNull NanosyntaxParser.StatementContext ctx) {

        // Second child is a semicolon (checked by ANTLR -- ignored)
        ParseTree child = ctx.getChild(0);

        if (child instanceof AssignmentContext) {
            logDebug("an assignment", ctx, child);
            return child.accept(assignmentVisitor);
        } else if (child instanceof PrimitiveContext) {
            logDebug("a primitive", ctx, child);
            return child.accept(primitiveVisitor);
        } else if (child instanceof IdContext) {
            logDebug("a stand-alone ID", ctx, child);
            return child.accept(standaloneVisitor);
        } else {
            throw new IllegalStateException("Unexpected statement child element " +
                child.getClass().getSimpleName());
        }
    }

    private void logDebug(String typeStr, ParseTree parent, ParseTree child) {
        logger.debug("Statement {} recognized as {}. Recurring on child {}.",
            parent.getText(), typeStr, child.getText());
    }
}
