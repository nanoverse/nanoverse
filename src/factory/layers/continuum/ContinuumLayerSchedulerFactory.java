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

package factory.layers.continuum;

import cells.BehaviorCell;
import control.identifiers.Coordinate;
import layers.continuum.*;
import layers.continuum.solvers.*;
import no.uib.cipr.matrix.Matrix;
import no.uib.cipr.matrix.DenseVector;
import org.dom4j.Element;
import structural.utilities.XmlUtil;

import java.util.IdentityHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by dbborens on 1/8/15.
 */
public abstract class ContinuumLayerSchedulerFactory {

    public static ContinuumLayerScheduler instantiate(Element e, ContinuumLayerContent content, Function<Coordinate, Integer> indexer, int n, String id) {
        boolean operators = !XmlUtil.getBoolean(e, "disable-operators");
        ScheduledOperations so = new ScheduledOperations(indexer, n, operators);
        AgentToOperatorHelper helper = new AgentToOperatorHelper(indexer, n, operators);
        ContinuumAgentManager agentManager = buildAgentManager(helper, so, id, operators);
        ContinuumSolver solver = makeSolver(e, content, so, operators);
        HoldManager holdManager = new HoldManager(agentManager, solver);
        return new ContinuumLayerScheduler(so, holdManager);
    }

    private static ContinuumSolver makeSolver(Element e, ContinuumLayerContent content, ScheduledOperations so, boolean operators) {
        if (e == null) {
            return ContinuumSolverFactory.instantiate(null, content, so, operators);
        }

        Element solverElement = e.element("solver");
        return ContinuumSolverFactory.instantiate(solverElement, content, so, operators);
    }

    private static ContinuumAgentManager buildAgentManager(AgentToOperatorHelper agentHelper, ScheduledOperations so, String id, boolean operators) {
        Consumer<DenseVector> injector = vector -> so.inject(vector);

        Consumer<Matrix> exponentiator;
        if (operators) {
            exponentiator = matrix -> so.apply(matrix);
        } else {
            exponentiator = matrix -> {
                throw new IllegalStateException("Attempted to schedule matrix operator, but operators are explicitly disabled");
            };
        }

        ReactionLoader agentScheduler = new ReactionLoader(injector, exponentiator, agentHelper, operators);

        IdentityHashMap<BehaviorCell, Supplier<RelationshipTuple>> map = new IdentityHashMap<>();
        ContinuumAgentIndex index = new ContinuumAgentIndex(map);
        ContinuumAgentManager ret = new ContinuumAgentManager(agentScheduler, index, id);
        return ret;
    }

}
