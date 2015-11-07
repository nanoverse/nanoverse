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
import nanoverse.compiler.pipeline.translate.symbol.DictionarySymbolTable;
import org.slf4j.*;

/**
 * Created by dbborens on 7/23/2015.
 */
public class DictionaryContainerVisitor {
    private final DictionaryChildLoader loader;
    private final Logger logger;

    public DictionaryContainerVisitor(DictionaryChildLoader loader) {
        logger = LoggerFactory.getLogger(DictionaryContainerVisitor.class);
        this.loader = loader;
    }

    public DictionaryContainerVisitor(TranslationCallback walker) {
        logger = LoggerFactory.getLogger(DictionaryContainerVisitor.class);
        this.loader = new DictionaryChildLoader(walker);
    }

    public ObjectNode translate(ASTNode toTranslate,
                                DictionarySymbolTable symbolTable) {
        logger.debug("Translating \"{}\" using DST for class {}",
            toTranslate.getIdentifier(), symbolTable.getBroadClass()
                .getSimpleName());

        DictionaryObjectNode node = new DictionaryObjectNode(symbolTable);

        // Visit each child.
        toTranslate.getChildren()
            .forEach(child ->
                loader.loadChild(child, symbolTable, node));

        return node;
    }


}
