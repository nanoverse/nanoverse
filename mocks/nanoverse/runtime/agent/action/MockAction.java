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

package nanoverse.runtime.agent.action;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.HashMap;

/**
 * Created by David B Borenstein on 1/22/14.
 */
public class MockAction extends Action {
    private int timesRun;
    private Agent callback = null;
    private int identifier = 0;
    private HashMap<Coordinate, Integer> callerCounts;
    private Coordinate lastCaller;

    public MockAction() {
        super(null, null);
        timesRun = 0;
        callerCounts = new HashMap<>();
    }

    public Coordinate getLastCaller() {
        return lastCaller;
    }

    public int getTimesRun() {
        return timesRun;
    }

    @Override
    public int hashCode() {
        return callback != null ? callback.hashCode() : 0;
    }

    @Override
    public void run(Coordinate caller) {
        timesRun++;
        lastCaller = caller;
        if (caller != null) {
            increment(caller);
        }
    }

    public Agent getCallback() {
        return callback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockAction that = (MockAction) o;

        if (identifier != that.identifier) {
            return false;
        }

        return true;
    }

    @Override
    public Action copy(Agent child) {
        MockAction clone = new MockAction();
        clone.setCallback(child);
        clone.setIdentifier(identifier);
        return clone;
    }

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    public void setCallback(Agent callback) {
        this.callback = callback;
    }

    private void increment(Coordinate caller) {
        if (!callerCounts.containsKey(caller)) {
            callerCounts.put(caller, 0);
        }

        int count = callerCounts.get(caller);
        callerCounts.put(caller, count + 1);
    }
}
