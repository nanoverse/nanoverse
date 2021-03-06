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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.layers.continuum.HoldManagerFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.layers.continuum.solvers.ContinuumSolver;

/**
 * Created by dbborens on 8/20/2015.
 */
public class HoldManagerLoader {
    private final HoldManagerInterpolator interpolator;
    private final HoldManagerFactory factory;

    public HoldManagerLoader() {
        interpolator = new HoldManagerInterpolator();
        factory = new HoldManagerFactory();
    }

    public HoldManagerLoader(HoldManagerInterpolator interpolator,
                             HoldManagerFactory factory) {

        this.interpolator = interpolator;
        this.factory = factory;
    }

    public HoldManager instantiate(MapObjectNode node,
                                   ContinuumLayerContent content,
                                   Geometry geometry,
                                   ScheduledOperations so) {

        AgentToOperatorHelper atoHelper = new AgentToOperatorHelper(geometry, so.isOperators());

        ReactionLinker linker = new ReactionLinker(so, atoHelper);

        String id = interpolator.id(node);
        ContinuumSolver solver = interpolator.solver(node, content, so);
        factory.setSolver(solver);

        ContinuumAgentManager agentManager = new ContinuumAgentManager(linker, id);
        factory.setManager(agentManager);

        return factory.build();
    }
}
