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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.io.serialize.binary.csw.*;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;

/**
 * Created by dbborens on 12/11/13.
 * <p>
 * ContinuumStateWriter encodes a binary file containing the
 * state of the model at specified time points.
 */
public class ContinuumStateWriter extends Serializer {

    private CSWExtremaHelper extremaHelper;
    private CSWFileHelper fileHelper;

    @FactoryTarget
    public ContinuumStateWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
    }

    @Override
    public void init() {
        super.init();

        Function<Integer, Coordinate> deindexer = lm.getDeindexer();

        extremaHelper = new CSWExtremaHelper(p, deindexer,
            lm.getContinuumLayerIds());

        fileHelper = new CSWFileHelper(p,
            lm.getContinuumLayerIds());

        CSWOverviewWriter.writeOverview(p.getInstancePath(),
            lm.getContinuumLayerIds());
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        fileHelper.close();
        extremaHelper.serialize();
    }

    @Override
    public void close() {
        // Doesn't do anything
    }

    @Override
    public void flush(StepState stepState) {
        extremaHelper.push(stepState);
        fileHelper.push(stepState);
    }

    public void init(CSWExtremaHelper extremaHelper,
                     CSWFileHelper fileHelper) {

        super.init();

        this.extremaHelper = extremaHelper;
        this.fileHelper = fileHelper;
    }

}
