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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.factory.processes.discrete.TriggerProcessFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.discrete.*;
import nanoverse.runtime.processes.discrete.filter.Filter;

/**
 * Created by dbborens on 8/3/2015.
 */
public class TriggerProcessLoader extends ProcessLoader<TriggerProcess> {
    private final TriggerProcessFactory factory;
    private final TriggerProcessInterpolator interpolator;

    public TriggerProcessLoader() {
        factory = new TriggerProcessFactory();
        interpolator = new TriggerProcessInterpolator();
    }

    public TriggerProcessLoader(TriggerProcessFactory factory,
                                TriggerProcessInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public TriggerProcess instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        AgentProcessArguments cpArguments = interpolator.cpArguments(node, lm, p);
        factory.setCpArguments(cpArguments);

        Filter filter = interpolator.filter(node, lm, p);
        factory.setFilter(filter);

        String behavior = interpolator.behavior(node);
        factory.setBehaviorName(behavior);

        Boolean requireNeighbors = interpolator.requireNeighbors(node, p.getRandom());
        factory.setRequireNeighbors(requireNeighbors);

        Boolean skipVacantSites = interpolator.skipVacant(node, p.getRandom());
        factory.setSkipVacant(skipVacantSites);

        return factory.build();
    }
}
