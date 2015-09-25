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

package nanoverse.runtime.agent.targets;

import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;

import java.util.*;

/**
 * Targets specify which nanoverse.runtime.cells should receive the consequences
 * of an Action.
 * Created by dbborens on 2/7/14.
 */
public class TargetCaller extends TargetRule {
    public TargetCaller(BehaviorCell callback, LayerManager layerManager, Filter filter, int maximum, Random random) {
        super(callback, layerManager, filter, maximum, random);
    }

    @Override
    public TargetRule clone(BehaviorCell child) {
        return new TargetCaller(child, layerManager, filter, maximum, random);
    }

    @Override
    protected List<Coordinate> getCandidates(BehaviorCell caller) {
        Coordinate coord = layerManager.getCellLayer().getLookupManager().getCellLocation(caller);

        ArrayList<Coordinate> ret = new ArrayList<>(1);
        ret.add(coord);
        return ret;
    }
}
