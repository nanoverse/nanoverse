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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.agent.action.helper.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.Random;

/**
 * Created by dbborens on 10/21/2015.
 */
public class PreferentialExpansionManager {

    private final DisplacementManager displacementManager;
    private final SelfTargetHighlighter stHighlighter;
    private final ExpandToTargetManager expansionManager;
    private final ActionIdentityManager identity;
    private final CloneToVacancyHelper vacancyHelper;

    public PreferentialExpansionManager(ActionIdentityManager identity,
                                        SelfTargetHighlighter stHighlighter,
                                        CoordAgentMapper mapper,
                                        Random random,
                                        DisplacementManager displacementManager) {

        this.identity = identity;
        this.stHighlighter = stHighlighter;
        this.displacementManager = displacementManager;
        expansionManager = new ExpandToTargetManager(identity, random, mapper, displacementManager);
        vacancyHelper = new CloneToVacancyHelper(identity, mapper);
    }

    public PreferentialExpansionManager(DisplacementManager displacementManager, SelfTargetHighlighter stHighlighter, ExpandToTargetManager expansionManager, ActionIdentityManager identity, CloneToVacancyHelper vacancyHelper) {
        this.displacementManager = displacementManager;
        this.stHighlighter = stHighlighter;
        this.expansionManager = expansionManager;
        this.identity = identity;
        this.vacancyHelper = vacancyHelper;
    }

    public void preferentialExpand(Coordinate target) throws HaltCondition {
        Coordinate origin = identity.getOwnLocation();

        // Find out the shortest shoving path available, given the specified
        // parent and its preferred progeny destination.
        DisplacementOption shortestOption = expansionManager.getShortestOption(target);

        // Create a vacancy either at the parent or child site, depending on
        // which had a shorter shoving path.
        expansionManager.doShove(shortestOption);

        // Now that the agents have been shoved toward the vacancy, the formerly
        // occupied site is now vacant.
        Coordinate newlyVacant = shortestOption.getOccupied();

        // Place a cloned cell at the newly vacated position.
        vacancyHelper.cloneToVacancy(newlyVacant);

        // Clean up out-of-bounds agents.
        displacementManager.removeImaginary();

        // Highlight the parent and target locations.
        stHighlighter.highlight(target, origin);
    }

}
