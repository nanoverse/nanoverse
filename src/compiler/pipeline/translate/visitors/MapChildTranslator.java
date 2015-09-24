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

import compiler.pipeline.interpret.nodes.ASTNode;
import compiler.pipeline.translate.helpers.TranslationCallback;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;

/**
 * Created by dbborens on 9/22/2015.
 */
public class MapChildTranslator {

    private final TranslationCallback walker;
    private final MapGrandchildVisitor gcResolver;

    public MapChildTranslator(TranslationCallback walker) {
        this.walker = walker;
        gcResolver = new MapGrandchildVisitor(walker);
    }

    public MapChildTranslator(TranslationCallback walker,
                              MapGrandchildVisitor gcResolver) {
        this.walker = walker;
        this.gcResolver = gcResolver;
    }

    public ObjectNode translate(ASTNode child, ResolvingSymbolTable childRST) {
        ObjectNode childNode;

        // If the child is a list or dictionary, pass it back to be resolved.
        if (isCompound(childRST)) {
            childNode = walker.walk(child, childRST);
        } else {
            childNode = gcResolver.walk(child, childRST);
        }

        return childNode;
    }

    private boolean isCompound(ResolvingSymbolTable childRST) {
        if (childRST instanceof ListSymbolTable) {
            return true;
        }

        if (childRST instanceof DictionarySymbolTable) {
            return true;
        }

        return false;
    }
}
