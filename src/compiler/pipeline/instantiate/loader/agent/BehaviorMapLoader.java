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

package compiler.pipeline.instantiate.loader.agent;

import agent.Behavior;
import agent.action.BehaviorDescriptor;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.DictionaryObjectNode;
import compiler.pipeline.translate.symbol.DictionarySymbolTable;
import control.GeneralParameters;
import layers.LayerManager;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by dbborens on 8/25/2015.
 */
public class BehaviorMapLoader extends Loader<Map<String, BehaviorDescriptor>> {

    private final BehaviorMapChildLoader childLoader;

    public BehaviorMapLoader() {
        childLoader = new BehaviorMapChildLoader();
    }

    public BehaviorMapLoader(BehaviorMapChildLoader childLoader) {
        this.childLoader = childLoader;
    }

    public Map<String, BehaviorDescriptor> instantiate(DictionaryObjectNode node,
                                                       LayerManager lm,
                                                       GeneralParameters p) {

        Map<String, BehaviorDescriptor> ret = node
            .getMemberIdentifiers()
            .collect(Collectors.toMap(
                id -> id,
                id -> childLoader.load(id, node, lm, p)
            ));

        return ret;
    }

}
