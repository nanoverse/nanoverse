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
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.*;
import org.slf4j.*;

/**
 * Created by dbborens on 9/22/2015.
 */
public class ListChildLoader {

    private final TranslationCallback walker;
    private final Logger logger;

    public ListChildLoader(TranslationCallback walker) {
        this.walker = walker;
        logger = LoggerFactory.getLogger(ListChildLoader.class);
    }

    public void loadChild(ASTNode child, ListSymbolTable symbolTable, ListObjectNode node) {
        // The child's identifier is an instantiable subclass of
        // the list class.
        String identifier = child.getIdentifier();

        InstantiableSymbolTable childIST =
            symbolTable.getSymbolTable(identifier);

        ObjectNode childNode = walker.walk(child, childIST);

        logger.debug("Loading new {} to list of {}",
            childNode.getInstantiatingClass().getSimpleName(),
            symbolTable.getBroadClass().getSimpleName());

        node.loadMember(childNode);
    }
}
