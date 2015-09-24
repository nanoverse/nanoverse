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
public class NanoSingletonVisitor extends AbstractNanoNodeVisitor {

    private final Logger logger;
    private final NanoStandaloneIdVisitor idVisitor;
    private final NanoPrimitiveVisitor primitiveVisitor;
    private final NanoAssignmentVisitor assignmentVisitor;

    public NanoSingletonVisitor(NanoAssignmentVisitor assignmentVisitor) {
        this(new NanoStandaloneIdVisitor(),
             new NanoPrimitiveVisitor(), assignmentVisitor);
    }

    public NanoSingletonVisitor(NanoStandaloneIdVisitor idVisitor,
                                NanoPrimitiveVisitor primitiveVisitor,
                                NanoAssignmentVisitor assignmentVisitor) {

        logger = LoggerFactory.getLogger(NanoSingletonVisitor.class);
        this.idVisitor = idVisitor;
        this.primitiveVisitor = primitiveVisitor;
        this.assignmentVisitor = assignmentVisitor;
    }

    @Override
    public ASTNode visitSingleton(@NotNull NanosyntaxParser.SingletonContext ctx) {
//        logger.debug("Visiting singleton: {}", ctx.getText());
        ParseTree child = ctx.getChild(0);
        if (child instanceof  PrimitiveContext) {
            logger.debug("Recognized singleton {} as a primitive. Recurring on child {}.", ctx.getText(), child.getText());
            return child.accept(primitiveVisitor);
        } else if (child instanceof IdContext) {
            logger.debug("Recognized singleton {} as a stand-alone ID. Recurring on child {}.", ctx.getText(), child.getText());
            return child.accept(idVisitor);
        } else if (child instanceof AssignmentContext) {
            logger.debug("Recognized singleton {} as an assignment. Recurring on child {}.", ctx.getText(), child.getText());
            return child.accept(assignmentVisitor);
        } else {
            throw new IllegalStateException("Unexpected singleton child element " +
                    child.getClass().getSimpleName());
        }
    }
}
