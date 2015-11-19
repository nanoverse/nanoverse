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

package nanoverse.compiler.pipeline.instantiate.loader.agent.action;

import nanoverse.compiler.pipeline.instantiate.loader.agent.action.*;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.runtime.agent.action.ActionDescriptor;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/25/2015.
 */
public class FlexibleActionLoader {

    private final CompoundActionLoader compoundActionLoader;

    public FlexibleActionLoader() {
        compoundActionLoader = new CompoundActionLoader();
    }


    public FlexibleActionLoader(CompoundActionLoader compoundActionLoader) {
        this.compoundActionLoader = compoundActionLoader;
    }

    public ActionDescriptor load(ObjectNode child,
                                 LayerManager lm,
                                 GeneralParameters p) {

        if (child instanceof ListObjectNode) {
            return compoundAction(child, lm, p);
        }

        return singletonAction(child, lm, p);
    }

    private ActionDescriptor singletonAction(ObjectNode child, LayerManager lm, GeneralParameters p) {
        MapObjectNode cNode = (MapObjectNode) child;
        InstantiableSymbolTable ist = cNode.getSymbolTable();
        ActionLoader loader = (ActionLoader) ist.getLoader();
        return loader.instantiate(cNode, lm, p);
    }

    // If the user supplies a list of actions instead of a single action,
    // treat it as an implicit compound action
    private ActionDescriptor compoundAction(ObjectNode child, LayerManager lm, GeneralParameters p) {
        ListObjectNode cNode = (ListObjectNode) child;
        return compoundActionLoader.instantiate(cNode, lm, p);
    }
}
