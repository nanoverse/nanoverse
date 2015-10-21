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

package nanoverse.runtime.cells;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * LEGACY -- DO NOT USE
 *
 * Mock cell class used for testing.
 *
 * Created by dbborens on 1/13/14.
 */
public class MockAgent extends Agent {

    private MockAgent child;
    private String name = "mock";
    private String lastTriggeredBehaviorName;
    private Coordinate lastTriggeredCaller;
    private boolean died;
    private int triggerCount = 0;

    public MockAgent() {
        super();
    }

    public MockAgent(String name) {
        super();
        this.name = name;
    }

    @Override
    public MockAgent copy() {
        return child;
    }

    @Override
    public void trigger(String behaviorName, Coordinate caller) throws HaltCondition {
        lastTriggeredBehaviorName = behaviorName;
        lastTriggeredCaller = caller;
        triggerCount++;
    }

    @Override
    public void die() {
        died = true;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MockAgent)) {
            return false;
        }

        MockAgent other = (MockAgent) obj;

        if (!other.name.equals(name)) {
            return false;
        }

        return true;
    }

    public void setChild(MockAgent child) {
        this.child = child;
    }

    public Coordinate getLastTriggeredCaller() {
        return lastTriggeredCaller;
    }

    public String getLastTriggeredBehaviorName() {
        return lastTriggeredBehaviorName;
    }

    public int getTriggerCount() {
        return triggerCount;
    }
}
