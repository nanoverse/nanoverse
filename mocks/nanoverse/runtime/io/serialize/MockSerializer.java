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

package nanoverse.runtime.io.serialize;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.StepState;

/**
 * Created by dbborens on 5/15/14.
 */
public class MockSerializer extends Serializer {
    private boolean isDispatchHalt;
    private boolean isClose;
    private boolean isInit;
    private boolean isFlush;

    public MockSerializer(LayerManager layerManager) {
        super(null, layerManager);

        isDispatchHalt = false;
        isClose = false;
        isInit = false;
        isFlush = false;
    }

    public boolean isDispatchHalt() {
        return isDispatchHalt;
    }

    public boolean isClose() {
        return isClose;
    }

    public boolean isInit() {
        return isInit;
    }

    public boolean isFlush() {
        return isFlush;
    }

    @Override
    public void dispatchHalt(HaltCondition ex) {
        isDispatchHalt = true;
    }

    @Override
    public void close() {
        isClose = true;
    }

    @Override
    public void init() {
        isInit = true;
    }


    @Override
    public void flush(StepState stepState) {
        isFlush = true;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MockSerializer);
    }
}
