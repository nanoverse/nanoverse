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

import compiler.error.SyntaxError;
import compiler.pipeline.interpret.nodes.*;
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.pipeline.translate.nodes.*;
import compiler.pipeline.translate.symbol.*;
import org.slf4j.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 7/23/2015.
 */
public class DictionaryContainerVisitor {
    private final TranslationCallback walker;
    private final Logger logger;

    public DictionaryContainerVisitor(TranslationCallback walker) {
        logger = LoggerFactory.getLogger(DictionaryContainerVisitor.class);
        this.walker = walker;
    }

    public ObjectNode translate(ASTNode toTranslate, DictionarySymbolTable symbolTable) {
        logger.debug("Translating \"{}\" using DST for class {}", toTranslate.getIdentifier(),
                symbolTable.getBroadClass().getSimpleName());

        DictionaryObjectNode node = new DictionaryObjectNode(symbolTable);

        // Visit each child.
        toTranslate.getChildren()
                .forEach(child -> {

                    // The child's identifier is a unique name for the element.
                    String elementName = child.getIdentifier();

                    ObjectNode childNode = translateChild(child, symbolTable);
                    node.loadMember(elementName, childNode);
                });
        return node;
    }

    private ObjectNode translateChild(ASTNode child, DictionarySymbolTable symbolTable) {
        // The child's value is an instantiable value of the subclass of the
        // list class.
        ASTNode childValue = getChildValue(child);
        String childClass = childValue.getIdentifier();
        InstantiableSymbolTable ist = symbolTable.getSymbolTable(childClass);
        ObjectNode childNode = walker.walk(childValue, ist);
        return childNode;
    }

    private ASTNode getChildValue(ASTNode child) {
        List<ASTNode> grandchildren = child
                .getChildren()
                .collect(Collectors.toList());

        if (grandchildren.size() != 1) {
            throw new SyntaxError("Unexpected dictionary value on element " + child.getIdentifier() + ". Detail:\n\n" + child.toString());
        }
        return grandchildren.get(0);
    }
}
