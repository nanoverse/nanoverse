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
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.*;

import java.util.stream.Stream;

/**
 * Visits an identifier that represents an object with
 * no defined properties. This is not always the case
 * with identifier contexts; they are also used to
 * represent the key in an assignment context, in which
 * case they are turned into strings.
 * <p>
 * Created by dbborens on 4/22/15.
 */
public class NanoStandaloneIdVisitor extends AbstractNanoNodeVisitor {

    private final Logger logger;

    public NanoStandaloneIdVisitor() {
        logger = LoggerFactory.getLogger(NanoStandaloneIdVisitor.class);
    }

    @Override
    public ASTNode visitId(@NotNull NanosyntaxParser.IdContext ctx) {
        ParseTree idTree = ctx.getChild(0);
        verifyPayload(idTree, CommonToken.class);
        String identifier = idTree.getText();
        logger.debug("Translated stand-alone ID \"{}\" as \"{}\"", ctx.getText(), idTree.getText());
        return new ASTNode(identifier, Stream.empty());
    }
}