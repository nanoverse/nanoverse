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
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.slf4j.*;

/**
 * Created by dbborens on 9/22/2015.
 */
public class MapChildLoader {


    private final Logger logger;
    private final MapChildTranslator translator;

    public MapChildLoader(TranslationCallback walker) {
        translator = new MapChildTranslator(walker);
        logger = LoggerFactory.getLogger(MapChildLoader.class);
    }

    public MapChildLoader(MapChildTranslator translator) {
        this.translator = translator;
        logger = LoggerFactory.getLogger(MapChildLoader.class);
    }

    public void loadChild(ASTNode child, MapSymbolTable symbolTable, MapObjectNode node) {
        // The child's identifier is a field of this object.
        String identifier = child.getIdentifier();

        // Get a symbol table that can resolve the field's value.
        ResolvingSymbolTable childRST = symbolTable.getSymbolTable(identifier);

        ObjectNode childNode = translator.translate(child, childRST);
        logDebug(identifier, childNode);

        node.loadMember(identifier, childNode);
    }

    private void logDebug(String identifier, ObjectNode childNode) {
        logger.debug("Loading new {} to property \"{}\"",
            childNode.getInstantiatingClass().getSimpleName(),
            identifier);
    }

}
