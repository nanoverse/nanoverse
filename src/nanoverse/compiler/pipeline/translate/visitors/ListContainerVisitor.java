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

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.helpers.TranslationCallback;
import nanoverse.compiler.pipeline.translate.nodes.ListObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.ListSymbolTable;
import org.slf4j.*;

/**
 * Takes a container node and a list symbol table, and returns
 * a list object node.
 * <p>
 * Created by dbborens on 4/22/15.
 */
public class ListContainerVisitor {

    private final ListChildLoader loader;
    private final Logger logger;

    public ListContainerVisitor(TranslationCallback walker) {
        logger = LoggerFactory.getLogger(ListContainerVisitor.class);
        loader = new ListChildLoader(walker);
    }

    public ListContainerVisitor(ListChildLoader loader) {
        logger = LoggerFactory.getLogger(ListContainerVisitor.class);
        this.loader = loader;
    }

    public ListObjectNode translate(ASTNode toTranslate, ListSymbolTable symbolTable) {
        logger.debug("Translating {} using LST for class {}", toTranslate.getIdentifier(),
            symbolTable.getBroadClass().getSimpleName());

        ListObjectNode node = new ListObjectNode(symbolTable, toTranslate.getLineNumber());

        // Visit each child.
        toTranslate.getChildren()
            .forEach(child -> loader.loadChild(child, symbolTable, node));

        return node;
    }
}
