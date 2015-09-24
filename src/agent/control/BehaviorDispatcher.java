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

import agent.action.Action;
import cells.BehaviorCell;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * BehaviorDispatcher is a map between behavior names and the
 * behaviors themselves. It is associated with a particular cell,
 * and can be used to trigger behaviors in that cell.
 * <p>
 * Created by David B Borenstein on 1/21/14.
 */
public class BehaviorDispatcher {
    private HashMap<String, Action> behaviors;

    public BehaviorDispatcher() {
        behaviors = new HashMap<>();
    }


    public void map(String name, Action behavior) {
        behaviors.put(name, behavior);
    }

    /**
     * Trigger a behavior associated with the cell.
     *
     * @param behaviorName
     * @param caller       The coordinate from which the call originated. If
     *                     the call originated with a top-down process, the
     *                     caller will be null.
     */
    public void trigger(String behaviorName, Coordinate caller) throws HaltCondition {
        if (!behaviors.containsKey(behaviorName)) {
            throw new IllegalArgumentException("Action '" + behaviorName + "' not found.");
        }

        Action behavior = behaviors.get(behaviorName);
        behavior.run(caller);
    }

    public BehaviorDispatcher clone(BehaviorCell child) {
        BehaviorDispatcher clone = new BehaviorDispatcher();

        // Clone the behavior catalog item for item.
        for (String behaviorName : behaviors.keySet()) {
            Action b = behaviors.get(behaviorName);
            Action bc = b.clone(child);
            clone.map(behaviorName, bc);
        }

        return clone;
    }

    /**
     * A BehaviorDispatcher is equal to another object only if:
     * (1) The other Object is a BehaviorDispatcher.
     * (2) Each Action in the other BehaviorDispatcher
     * has an equivalent Action mapped to the same name
     * as this BehaviorDispatcher.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BehaviorDispatcher)) {
            return false;
        }

        BehaviorDispatcher other = (BehaviorDispatcher) obj;

        if (other.behaviors.size() != this.behaviors.size()) {
            return false;
        }

        for (String behaviorName : behaviors.keySet()) {
            if (!other.behaviors.containsKey(behaviorName)) {
                return false;
            }

            Action otherBehavior = other.behaviors.get(behaviorName);
            Action thisBehavior = this.behaviors.get(behaviorName);

            if (!thisBehavior.equals(otherBehavior)) {
                return false;
            }
        }

        return true;
    }

    public Action getMappedBehavior(String behaviorName) {
        return behaviors.get(behaviorName);
    }

    public Stream<String> getBehaviorNames() {
        return behaviors.keySet().stream();
    }

}
