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

package agent.control;

import agent.Behavior;
import control.identifiers.Coordinate;

import java.util.ArrayList;

/**
 * Created by David B Borenstein on 1/23/14.
 */
public class MockBehaviorDispatcher extends BehaviorDispatcher {

    private String lastTriggeredName;
    private Coordinate lastTriggeredCaller;
    private String lastMappedName;
    private Behavior lastMappedBehavior;
    private ArrayList<String> mappedNames;
    private ArrayList<Behavior> mappedBehaviors;
    private boolean reportEquals;
    private boolean overrideEquals;

    public MockBehaviorDispatcher() {
        mappedNames = new ArrayList<>();
        mappedBehaviors = new ArrayList<>();
    }

    public ArrayList<String> getMappedNames() {
        return mappedNames;
    }

    public ArrayList<Behavior> getMappedBehaviors() {
        return mappedBehaviors;
    }

    public Behavior getLastMappedBehavior() {
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
    public void map(String name, Behavior behavior) {
        mappedNames.add(name);
        mappedBehaviors.add(behavior);
        lastMappedName = name;
        lastMappedBehavior = behavior;
    }

    @Override
    public void trigger(String behaviorName, Coordinate caller) {
        lastTriggeredName = behaviorName;
        lastTriggeredCaller = caller;
    }

    public void setOverrideEquals(boolean overrideEquals) {
        this.overrideEquals = overrideEquals;

    }

    public void setReportEquals(boolean reportEquals) {
        this.reportEquals = reportEquals;
    }

    @Override
    public boolean equals(Object obj) {
        if (overrideEquals) {
            return reportEquals;
        } else {
            return super.equals(obj);
        }
    }
}
