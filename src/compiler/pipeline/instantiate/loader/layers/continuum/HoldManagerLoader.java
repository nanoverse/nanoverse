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

package compiler.pipeline.instantiate.loader.layers.continuum;

import compiler.pipeline.instantiate.factory.layers.continuum.HoldManagerFactory;
import compiler.pipeline.translate.nodes.MapObjectNode;
import geometry.Geometry;
import layers.continuum.*;
import layers.continuum.solvers.ContinuumSolver;

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
