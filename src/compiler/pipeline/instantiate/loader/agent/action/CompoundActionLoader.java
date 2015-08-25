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

package compiler.pipeline.instantiate.loader.agent.action;

import agent.action.*;
import compiler.pipeline.instantiate.factory.agent.action.CompoundActionFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import layers.LayerManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/3/2015.
 */
public class CompoundActionLoader extends Loader<CompoundActionDescriptor> {

    private final CompoundActionFactory factory;
    private final CompoundActionChildLoader childLoader;

    public CompoundActionLoader() {
        factory = new CompoundActionFactory();
        childLoader = new CompoundActionChildLoader();
    }

    public CompoundActionLoader(CompoundActionFactory factory,
                                CompoundActionChildLoader childLoader) {
        this.factory = factory;
        this.childLoader = childLoader;
    }

    public CompoundActionDescriptor instantiate(ListObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayerManager(lm);

        Stream<ActionDescriptor> children = node.getMemberStream()
            .map(o -> (MapObjectNode) o)
            .map(childNode -> childLoader.action(childNode, lm, p));

        factory.setChildren(children);
        
        return factory.build();
    }
}
