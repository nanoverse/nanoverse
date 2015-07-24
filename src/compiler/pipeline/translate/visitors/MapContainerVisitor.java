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

package compiler.pipeline.translate.visitors;

import compiler.pipeline.interpret.nodes.*;
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.tables.*;
import org.slf4j.*;

/**
 * Takes a container node and a map symbol table, and returns
 * a map object node.
 *
 * Created by dbborens on 4/22/15.
 */
public class MapContainerVisitor {

    private final TranslationCallback walker;
    private final Logger logger;

    public MapContainerVisitor(TranslationCallback walker) {
        logger = LoggerFactory.getLogger(MapContainerVisitor.class);
        this.walker = walker;
    }

    public ObjectNode translate(ASTContainerNode toTranslate, MapSymbolTable symbolTable) {
        logger.debug("Translating {} using MST for class {}", toTranslate.getIdentifier(),
                symbolTable.getInstanceClass().getSimpleName());

        MapObjectNode node = new MapObjectNode(symbolTable);

        // Visit each child.
        toTranslate.getChildren()

                .forEach(child -> {

                    // The child's identifier is a field of this object.
                    String identifier = child.getIdentifier();

                    // Get a symbol table that can resolve the field's value.
                    ResolvingSymbolTable childRST = symbolTable.getSymbolTable(identifier);

                    ObjectNode childNode;

                    // If the child is a list, pass it back to be resolved.
                    if (childRST instanceof ListSymbolTable) {
                        childNode = walker.walk(child, childRST);

                    // Otherwise, it is the granchild that will be resolved.
                    } else {

                        // The child will have exactly one child of its own: the instance.
                        ASTNode grandchild = child.getChildren().findFirst().get();

                        // The identifier of the grandchild can be resolved to an instance.
                        String gcIdentifier = grandchild.getIdentifier();
                        InstantiableSymbolTable childIST = childRST.getSymbolTable(gcIdentifier);

                        // Call back on this grandchild.
                        childNode = walker.walk(grandchild, childIST);
                    }

                    logger.debug("Loading new {} to property \"{}\"",
                            childNode.getInstantiatingClass().getSimpleName(),
                            identifier);

                    node.loadMember(identifier, childNode);
                });

        return node;
    }
}
