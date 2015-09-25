/*
 * Copyright (c) 2014, 2015 David Bruce Borenstein , C. Daniel
 * Greenidge, and the Trustees of Princeton University.
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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.control.arguments.Argument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.processes.*;

/**
 * Enforces Dirichlet boundary conditions. Make sure you place a hold on the
 * layer when this process is running!
 *
 * @author Daniel Greenidge
 */
public class DirichletBoundaryEnforcer extends ContinuumProcess {

    private final Argument<Double> value;
    private final String layerId;
    private final CoordinateSet activeSites;

    /**
     * Constructor.
     *
     * @param arguments   the base process arguments
     * @param value       the value at the boundary
     * @param layerId     the ID of the layer we want to enforce the boundary
     *                    condition on
     * @param activeSites the list of sites that make up the boundary
     */
    public DirichletBoundaryEnforcer(BaseProcessArguments arguments,
                                     Argument<Double> value, String layerId,
                                     CoordinateSet activeSites) {
        super(arguments);
        this.value = value;
        this.layerId = layerId;
        this.activeSites = activeSites;

    }

    @Override
    public void fire(StepState state) throws HaltCondition {

        Coordinate[] canonicalSites = getLayerManager().getCellLayer()
            .getGeometry().getCanonicalSites();
        ContinuumLayerScheduler scheduler = getLayerManager()
            .getContinuumLayer(layerId).getScheduler();

        for (Coordinate i : canonicalSites) {
            if (activeSites.contains(i)) {
                scheduler.setBoundaryCondition(i, value.next());
            }
        }
    }

    @Override
    public void init() {
        // Does nothing
    }
}
