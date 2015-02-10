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

package agent.targets;

import cells.BehaviorCell;
import control.identifiers.Coordinate;
import processes.discrete.filter.NullFilter;

import java.util.List;

/**
 * Created by dbborens on 2/14/14.
 */
public class MockTargetRule extends TargetRule {
    private List<Coordinate> targets;
    private BehaviorCell lastCaller;

    public MockTargetRule() {
        super(null, null, new NullFilter(), -1, null);
    }

    public void setTargets(List<Coordinate> targets) {
        this.targets = targets;
    }

    public BehaviorCell getLastCaller() {
        return lastCaller;
    }

    @Override
    protected List<Coordinate> getCandidates(BehaviorCell caller) {
        lastCaller = caller;
        return targets;
    }

    @Override
    public TargetRule clone(BehaviorCell child) {
        return new MockTargetRule();
    }
}
