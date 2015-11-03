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

package nanoverse.runtime.io.serialize.text;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.processes.StepState;

import java.util.*;

/**
 * Writes out the number of each "state" as a function of time.
 *
 * @author dbborens
 */
public class CensusWriter extends Serializer {

    private final CensusFlushHelper flushHelper;
    private final CensusWriteHelper writeHelper;

    public CensusWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);

        HashMap<Integer, HashMap<String, Integer>> histo = new HashMap<>();
        ArrayList<Integer> frames = new ArrayList<>();
        HashSet<String> observedNames = new HashSet<>();

        flushHelper = new CensusFlushHelper(observedNames, frames, histo);
        writeHelper = new CensusWriteHelper(observedNames, histo, p);
    }

    public CensusWriter(GeneralParameters p,
                        LayerManager lm,
                        CensusFlushHelper flushHelper,
                        CensusWriteHelper writeHelper) {
        super(p, lm);
        this.flushHelper = flushHelper;
        this.writeHelper = writeHelper;
    }

    @Override
    public void init() {
        flushHelper.init();
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        int t = (int) Math.round(ex.getGillespie());

        // For the final flush, we use the real (non-recorded) state of the
        // simulation.
        flushHelper.doFlush(lm.getAgentLayer(), t);
        writeHelper.commit();
    }

    @Override
    public void close() {
    }

    @Override
    public void flush(StepState stepState) {
        AgentLayer layer = stepState.getRecordedAgentLayer();
        flushHelper.doFlush(layer, stepState.getFrame());
    }
}
