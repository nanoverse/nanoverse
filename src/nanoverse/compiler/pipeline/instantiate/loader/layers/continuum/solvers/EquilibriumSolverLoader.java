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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum.solvers;

import nanoverse.compiler.pipeline.instantiate.factory.layers.continuum.solvers.EquilibriumSolverFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.layers.continuum.solvers.*;

/**
 * Created by dbborens on 8/1/2015.
 */
public class EquilibriumSolverLoader extends ContinuumSolverLoader<EquilibriumSolver> {
    private final EquilibriumSolverFactory factory;

    public EquilibriumSolverLoader() {
        factory = new EquilibriumSolverFactory();
    }

    public EquilibriumSolverLoader(EquilibriumSolverFactory factory) {
        this.factory = factory;
    }

    public ContinuumSolver instantiate(ContinuumLayerContent content, ScheduledOperations so) {
        return instantiate(null, content, so);
    }

    @Override
    public ContinuumSolver instantiate(MapObjectNode node, ContinuumLayerContent content, ScheduledOperations so) {
        factory.setContent(content);
        factory.setSo(so);
        return factory.build();
    }

}
