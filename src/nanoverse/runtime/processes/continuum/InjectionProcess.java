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

package nanoverse.runtime.processes.continuum;

import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CoordinateSet;
import nanoverse.runtime.processes.*;
import nanoverse.runtime.structural.annotations.FactoryTarget;
import no.uib.cipr.matrix.DenseVector;

/**
 * Created by dbborens on 6/4/2015.
 */
public class InjectionProcess extends ContinuumProcess {

    private final DoubleArgument valueArg;
    private final String layerId;
    private final CoordinateSet activeSites;

    @FactoryTarget
    public InjectionProcess(BaseProcessArguments arguments,
                            DoubleArgument valueArg, String layerId,
                            CoordinateSet activeSites) {
        super(arguments);
        this.valueArg = valueArg;
        this.layerId = layerId;
        this.activeSites = activeSites;
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        Coordinate[] canonicalSites = getLayerManager().getCellLayer()
            .getGeometry().getCanonicalSites();
        DenseVector source = new DenseVector(canonicalSites.length);

        for (int i = 0; i < canonicalSites.length; i++) {
            double value = valueArg.next();
            if (activeSites.contains(canonicalSites[i])) {
                source.set(i, value);
            }
        }

        getLayerManager().getContinuumLayer(layerId).getScheduler().inject(source);
    }

    @Override
    public void init() {
        // Does nothing
    }
}
