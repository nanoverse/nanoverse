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

package nanoverse.compiler.pipeline.translate.visitors;

import nanoverse.compiler.pipeline.interpret.nodes.ASTNode;
import nanoverse.compiler.pipeline.translate.helpers.TranslationCallback;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import org.slf4j.*;

/**
 * Takes a container node and a map symbol table, and returns
 * a map object node.
 * <p>
 * Created by dbborens on 4/22/15.
 */
public class MapContainerVisitor {

    private final MapChildLoader loader;
    private final Logger logger;

    public MapContainerVisitor(TranslationCallback walker) {
        logger = LoggerFactory.getLogger(MapContainerVisitor.class);
        loader = new MapChildLoader(walker);
    }

    public MapContainerVisitor(MapChildLoader loader) {
        logger = LoggerFactory.getLogger(MapContainerVisitor.class);
        this.loader = loader;
    }

    public ObjectNode translate(ASTNode toTranslate, MapSymbolTable symbolTable) {
        logger.debug("Translating {} using MST for class {}", toTranslate.getIdentifier(),
            symbolTable.getInstanceClass().getSimpleName());

        MapObjectNode node = new MapObjectNode(symbolTable);

        // Visit each child.
        toTranslate.getChildren()
            .forEach(child -> loader.loadChild(child, symbolTable, node));

        return node;
    }
}
