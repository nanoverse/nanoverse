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

package agent;

import agent.action.Action;
import cells.BehaviorCell;
import control.identifiers.Coordinate;

import java.util.HashMap;

/**
 * Created by David B Borenstein on 1/21/14.
 */
public class MockBehavior extends Behavior {

    private int timesRun;
    private HashMap<Coordinate, Integer> callerCounts;

    private Coordinate lastCaller = null;


    public MockBehavior() {
        super(null, null, null);
        timesRun = 0;
        callerCounts = new HashMap<>();
    }

    public MockBehavior(BehaviorCell callback) {
        super(callback, null, null);
        timesRun = 0;
        callerCounts = new HashMap<>();
    }

    public Coordinate getLastCaller() {
        return lastCaller;
    }

    @Override
    public void run(Coordinate caller) {
        timesRun++;

        lastCaller = caller;

        if (caller != null) {
            increment(caller);
        }
    }

    private void increment(Coordinate caller) {
        if (!callerCounts.containsKey(caller)) {
            callerCounts.put(caller, 0);
        }

        int count = callerCounts.get(caller);
        callerCounts.put(caller, count + 1);
    }

    public int getTimesRun() {
        return timesRun;
    }

    public int timesCaller(Coordinate caller) {
        if (!callerCounts.containsKey(caller))
            return 0;

        return callerCounts.get(caller);
    }

    @Override
    protected Action[] getActionSequence() {
        return new Action[0];
    }

    @Override
    public Behavior clone(BehaviorCell child) {
        return new MockBehavior(child);
    }

}
