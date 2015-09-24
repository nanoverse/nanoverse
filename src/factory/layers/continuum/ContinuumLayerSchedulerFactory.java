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

import geometry.Geometry;
import layers.continuum.*;
import layers.continuum.solvers.ContinuumSolver;
import org.dom4j.Element;
import structural.utilities.XmlUtil;

/**
 * Created by dbborens on 1/8/15.
 */
public abstract class ContinuumLayerSchedulerFactory {

    public static ContinuumLayerScheduler instantiate(Element e, ContinuumLayerContent content, Geometry geom, String id) {
        boolean operators = !XmlUtil.getBoolean(e, "disable-operators");
        ScheduledOperations so = new ScheduledOperations(geom, operators);
        AgentToOperatorHelper helper = new AgentToOperatorHelper(geom, operators);
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
        ReactionLinker agentScheduler = new ReactionLinker(so, agentHelper);
        ContinuumAgentManager ret = new ContinuumAgentManager(agentScheduler, id);
        return ret;
    }

}
