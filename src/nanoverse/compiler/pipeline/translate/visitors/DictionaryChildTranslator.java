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
import nanoverse.compiler.pipeline.translate.nodes.ObjectNode;
import nanoverse.compiler.pipeline.translate.symbol.*;

/**
 * Created by dbborens on 9/22/2015.
 */
public class DictionaryChildTranslator {

    private final TranslationCallback walker;
    private final GrandchildResolver resolver;

    public DictionaryChildTranslator(TranslationCallback walker,
                                     GrandchildResolver resolver) {

        this.walker = walker;
        this.resolver = resolver;
    }

    public DictionaryChildTranslator(TranslationCallback walker) {

        this.walker = walker;
        resolver = new GrandchildResolver();
    }

    public ObjectNode translateChild(ASTNode child, DictionarySymbolTable symbolTable) {
        // The child's value is an instantiable value of the subclass of the
        // list class.
        ASTNode grandchild = resolver.getChildValue(child);
        String classId = grandchild.getIdentifier();
        InstantiableSymbolTable ist = symbolTable.getSymbolTable(classId, child.lineNumber());
        ObjectNode childNode = walker.walk(grandchild, ist);
        return childNode;
    }

}
