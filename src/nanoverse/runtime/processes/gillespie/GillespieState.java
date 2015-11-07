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

package nanoverse.runtime.processes.gillespie;

import java.util.HashMap;

/**
 * @author David Bruce Borenstein
 * @tested GillespieTest.java
 */
public class GillespieState {

    boolean closed = false;
    // Map of process ID --> weight
    private HashMap<Integer, Double> weightMap;
    // Map of process ID --> count
    private HashMap<Integer, Integer> countMap;
    private Integer[] expectedKeys;

    public GillespieState(Integer[] expectedKeys) {
        this.expectedKeys = expectedKeys;

        weightMap = new HashMap<>(expectedKeys.length);
        countMap = new HashMap<>(expectedKeys.length);
    }

    public void add(Integer processId, Integer eventCount, Double weight) {
        if (closed) {
            throw new IllegalStateException("Attempted to modify Gillespie state after it was closed.");
        }

        if (weightMap.containsKey(processId) || countMap.containsKey(processId)) {
            throw new IllegalStateException("Repeat assignment to Gillespie state.");
        }

        weightMap.put(processId, weight);
        countMap.put(processId, eventCount);
    }

    /**
     * Closes the state object to further additions, paving the way for
     * choosing an event to fire. This must be called before using getters
     * (except getKeys).
     */
    public void close() {
        if (closed) {
            throw new IllegalStateException("Repeat call to close() in Gillespie process state.");
        }

        if (weightMap.keySet().size() != expectedKeys.length) {
            throw new IllegalStateException("Gillespie state consistency failure.");
        }

        closed = true;
    }

    public Integer[] getKeys() {
        return expectedKeys.clone();
    }

    /**
     * Returns the total weight of all nanoverse.runtime.processes.
     *
     * @return
     */
    public double getTotalWeight() {
        if (!closed) {
            throw new IllegalStateException("Attempted to access Gillespie process state before it was ready.");
        }

        double total = 0;
        for (Integer key : expectedKeys) {
            total += weightMap.get(key);
        }

        return total;
    }

    public double getWeight(Integer processId) {
        if (!closed) {
            throw new IllegalStateException("Attempted to access Gillespie process state before it was ready.");
        }

        return weightMap.get(processId);
    }

    /**
     * Returns number of unique events possible for a given
     * process.
     *
     * @param processID
     */
    public int getEventCount(Integer processID) {
        if (!closed) {
            throw new IllegalStateException("Attempted to access Gillespie process state before it was ready.");
        }

        return countMap.get(processID);

    }

    public boolean isClosed() {
        return closed;
    }

    public int getTotalCount() {
        if (!closed) {
            throw new IllegalStateException("Attempted to access Gillespie process state before it was ready.");
        }

        int totalCount = 0;

        for (Integer key : countMap.keySet()) {
            totalCount += countMap.get(key);
        }

        return totalCount;
    }


}
