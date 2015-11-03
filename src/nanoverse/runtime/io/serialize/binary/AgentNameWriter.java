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

package nanoverse.runtime.io.serialize.binary;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.io.serialize.Serializer;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 10/22/2015.
 */
public class AgentNameWriter extends Serializer {

    private final AgentNameIndexManager indexManager;
    private final AgentNameIOHelper ioHelper;


    @FactoryTarget
    public AgentNameWriter(GeneralParameters p, LayerManager lm) {
        super(p, lm);
        indexManager = new AgentNameIndexManager();
        ioHelper = new AgentNameIOHelper(p, indexManager);
    }

    public AgentNameWriter(GeneralParameters p,
                           LayerManager lm,
                           AgentNameIndexManager indexManager,
                           AgentNameIOHelper ioHelper) {
        super(p, lm);
        this.indexManager = indexManager;
        this.ioHelper = ioHelper;
    }

    @Override
    public void init() {
        indexManager.init();
        ioHelper.init();
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        ioHelper.conclude();
    }

    @Override
    public void close() {
    }

    @Override
    public void flush(StepState stepState) {
        ioHelper.commit(stepState);
    }

}
