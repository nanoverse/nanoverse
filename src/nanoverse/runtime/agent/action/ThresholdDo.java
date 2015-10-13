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

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.arguments.DoubleArgument;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;

/**
 * Performs the specified child action iff the specified continuum layer has a
 * local value above the minimum and below the maximum. If a minimum or maximum
 * is omitted, it is set to negative or positive infinity respectively.
 * <p>
 * Created by dbborens on 6/4/2015.
 */
public class ThresholdDo extends Action {

    private final DoubleArgument minimumArg;
    private final DoubleArgument maximumArg;
    private final String layerId;
    private final Action child;

    public ThresholdDo(BehaviorCell callback, LayerManager layerManager, String layerId, DoubleArgument minimumArg, DoubleArgument maximumArg, Action child) {
        super(callback, layerManager);
        if (layerManager.getContinuumLayer(layerId) == null) {
            throw new IllegalArgumentException("Unrecognized continuum layer '"
                + layerId + "' in ThresholdDo");
        }

        this.minimumArg = minimumArg;
        this.maximumArg = maximumArg;
        this.layerId = layerId;
        this.child = child;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        if (!callbackExists()) {
            return;
        }
        Coordinate c = getOwnLocation().canonicalize();
        double minimum = minimumArg.next();
        double maximum = maximumArg.next();
        double value = getLayerManager()
            .getContinuumLayer(layerId)
            .getValueAt(c);

        if (value <= minimum) {
            return;
        }

        if (value >= maximum) {
            return;
        }

        child.run(caller);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ThresholdDo that = (ThresholdDo) o;

        if (!minimumArg.equals(that.minimumArg)) return false;
        if (!maximumArg.equals(that.maximumArg)) return false;
        if (!layerId.equals(that.layerId)) return false;
        return child.equals(that.child);
    }

    @Override
    public Action clone(BehaviorCell clonedCell) {
        Action clonedAction = child.clone(clonedCell);
        return new ThresholdDo(clonedCell, getLayerManager(), layerId, minimumArg, maximumArg, clonedAction);
    }

    @Override
    public int hashCode() {
        int result = minimumArg.hashCode();
        result = 31 * result + maximumArg.hashCode();
        result = 31 * result + layerId.hashCode();
        result = 31 * result + child.hashCode();
        return result;
    }
}
