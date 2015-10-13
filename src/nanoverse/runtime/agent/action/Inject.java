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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.BehaviorAgent;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 6/3/15.
 */
public class Inject extends Action {

    private final DoubleArgument deltaArg;
    private final String layerId;

    public Inject(BehaviorAgent callback, LayerManager layerManager, String layerId, DoubleArgument deltaArg) {
        super(callback, layerManager);
        this.deltaArg = deltaArg;
        this.layerId = layerId;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        if (!callbackExists()) {
            return;
        }

        Coordinate destination = getOwnLocation();
        double delta = deltaArg.next();
        getLayerManager()
            .getContinuumLayer(layerId)
            .getScheduler()
            .inject(destination, delta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inject inject = (Inject) o;

        if (!deltaArg.equals(inject.deltaArg)) return false;
        if (!layerId.equals(inject.layerId)) return false;

        return true;
    }

    @Override
    public Action clone(BehaviorAgent child) {
        return new Inject(child, getLayerManager(), layerId, deltaArg);
    }

    @Override
    public int hashCode() {
        int result = deltaArg.hashCode();
        result = 31 * result + layerId.hashCode();
        return result;
    }
}
