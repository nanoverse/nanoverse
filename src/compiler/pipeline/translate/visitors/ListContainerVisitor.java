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

import compiler.pipeline.interpret.nodes.ASTContainerNode;
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.symbol.tables.*;
import org.slf4j.*;

/**
 * Takes a container node and a list symbol table, and returns
 * a list object node.
 *
 * Created by dbborens on 4/22/15.
 */
public class ListContainerVisitor {

    private final TranslationCallback walker;
    private final Logger logger;

    public ListContainerVisitor(TranslationCallback walker) {
        logger = LoggerFactory.getLogger(ListContainerVisitor.class);
        this.walker = walker;
    }

    public ObjectNode translate(ASTContainerNode toTranslate, ListSymbolTable symbolTable) {
        logger.debug("Translating {} using LST for class {}", toTranslate.getIdentifier(),
                symbolTable.getMemberClass().getSimpleName());

        ListObjectNode node = new ListObjectNode(symbolTable);
        toTranslate.getChildren()
                .forEach(child -> {

                    // The child's identifier is an instantiable subclass of
                    // the list class.
                    String identifier = child.getIdentifier();

                    InstantiableSymbolTable childIST =
                            symbolTable.getSymbolTable(identifier);

                    ObjectNode childNode = walker.walk(child, childIST);

                    logger.debug("Loading new {} to list of {}",
                            childNode.getInstantiatingClass().getSimpleName(),
                            symbolTable.getMemberClass().getSimpleName());

                    node.loadMember(childNode);
                });

        return node;
    }
}
