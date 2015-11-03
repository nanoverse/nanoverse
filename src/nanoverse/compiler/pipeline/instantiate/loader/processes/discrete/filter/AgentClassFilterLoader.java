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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete.filter;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.filter.AgentClassFilterFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.AgentNameFilter;

/**
 * Created by dbborens on 8/24/2015.
 */
public class AgentClassFilterLoader extends FilterLoader<AgentNameFilter> {

    private final AgentClassFilterFactory factory;
    private final AgentNameFilterInterpolator interpolator;

    public AgentClassFilterLoader() {
        factory = new AgentClassFilterFactory();
        interpolator = new AgentNameFilterInterpolator();
    }

    public AgentClassFilterLoader(AgentClassFilterFactory factory,
                                 AgentNameFilterInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public AgentNameFilter instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        factory.setLayer(lm.getAgentLayer());

        String name = interpolator.name(node);
        factory.setToChoose(name);

        return factory.build();
    }
}
