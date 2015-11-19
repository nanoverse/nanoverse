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

package nanoverse.runtime.agent.control;

import nanoverse.runtime.agent.action.Action;
import nanoverse.runtime.control.identifiers.Coordinate;

import java.util.ArrayList;

/**
 * Created by David B Borenstein on 1/23/14.
 */
public class MockBehaviorDispatcher extends BehaviorDispatcher {

    private String lastTriggeredName;
    private Coordinate lastTriggeredCaller;
    private String lastMappedName;
    private Action lastMappedBehavior;
    private ArrayList<String> mappedNames;
    private ArrayList<Action> mappedBehaviors;
    private boolean reportEquals;
    private boolean overrideEquals;

    public MockBehaviorDispatcher() {
        mappedNames = new ArrayList<>();
        mappedBehaviors = new ArrayList<>();
    }

    public ArrayList<String> getMappedNames() {
        return mappedNames;
    }

    public ArrayList<Action> getMappedBehaviors() {
        return mappedBehaviors;
    }

    public Action getLastMappedBehavior() {
        return lastMappedBehavior;
    }

    public String getLastMappedName() {
        return lastMappedName;
    }

    public Coordinate getLastTriggeredCaller() {
        return lastTriggeredCaller;
    }

    public String getLastTriggeredName() {
        return lastTriggeredName;
    }

    @Override
    public void trigger(String behaviorName, Coordinate caller, String name) {
        lastTriggeredName = behaviorName;
        lastTriggeredCaller = caller;
    }

    @Override
    public void map(String name, Action behavior) {
        mappedNames.add(name);
        mappedBehaviors.add(behavior);
        lastMappedName = name;
        lastMappedBehavior = behavior;
    }

    @Override
    public boolean equals(Object obj) {
        if (overrideEquals) {
            return reportEquals;
        } else {
            return super.equals(obj);
        }
    }

    public void setOverrideEquals(boolean overrideEquals) {
        this.overrideEquals = overrideEquals;

    }

    public void setReportEquals(boolean reportEquals) {
        this.reportEquals = reportEquals;
    }
}
