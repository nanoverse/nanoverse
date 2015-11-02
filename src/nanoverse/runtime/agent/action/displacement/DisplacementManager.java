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

package nanoverse.runtime.agent.action.displacement;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.*;

/**
 * Created by dbborens on 5/14/14.
 */
public class DisplacementManager {

    private final ImaginarySiteCleaner cleaner;
    private final ShoveManager shoveManager;
    private final VacancyChooser vacancyChooser;

    @FactoryTarget
    public DisplacementManager(AgentLayer layer, Random random) {
        cleaner = new ImaginarySiteCleaner(layer);
        shoveManager = new ShoveManager(layer, random);
        vacancyChooser = new VacancyChooser(layer, random);
    }

    public DisplacementManager(ImaginarySiteCleaner cleaner,
                               ShoveManager shoveManager,
                               VacancyChooser vacancyChooser) {
        this.cleaner = cleaner;
        this.shoveManager = shoveManager;
        this.vacancyChooser = vacancyChooser;
    }

    /**
     * Push the row of agents at origin toward target, such that origin
     * winds up vacant. Return a list of affected agents.
     *
     * @param origin The site to become vacant.
     * @param target A currently unoccupied site that will become occupied at
     *               the end of the shove process. The entire line of agents,
     *               including the agent at the origin, will have been pushed
     *               in the direction of the target.
     * @return A set of coordinates that were affected by the shove operation.
     */
    public HashSet<Coordinate> shove(Coordinate origin, Coordinate target) throws HaltCondition {
        return shoveManager.shove(origin, target);
    }

    /**
     * Remove all out-of-bounds agents from the system. Useful after a shoving
     * operation.
     */
    public void removeImaginary() {
        cleaner.removeImaginary();
    }

    /**
     * Gets the set of all nearest vacancies to the agent, and chooses randomly
     * between them.
     *
     * @param origin
     * @return
     * @throws HaltCondition
     */
    public Coordinate chooseVacancy(Coordinate origin) throws HaltCondition {
        return vacancyChooser.chooseVacancy(origin);
    }

    /**
     * shoves starting at the origin in a randomly chosen cardinal direction until
     * a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveRandom(Coordinate origin) throws HaltCondition {
        return shoveManager.shoveRandom(origin);
    }


    /**
     * shoves starting at the origin in a cardinal direction chosen by weight to nearest
     * vacancy along that direction. shoves until a vacancy is reached or failure.
     *
     * @param origin
     * @return affectedSites
     * @throws HaltCondition
     */
    public HashSet<Coordinate> shoveWeighted(Coordinate origin) throws HaltCondition {
        return shoveManager.shoveWeighted(origin);
    }

}
