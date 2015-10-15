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

package nanoverse.runtime.agent;

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;

/**
 * Parent class for agents.
 *
 * @author David Bruce Borenstein
 */
public abstract class AbstractAgent {
    private String name;

    public String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Instructs the cell to divide and returns the daughter cell.
     * This triggers any division-related behaviors.
     * <p>
     * DO NOT CALL THIS METHOD DIRECTLY
     * FROM A PROCESS. Instead, use lattice.divide().
     *
     * @return
     */
    public abstract AbstractAgent divide() throws HaltCondition;

    public AbstractAgent replicate() throws HaltCondition {
        return clone(name);
    }

    /**
     * Creates an exact replica of the cell.
     *
     * @return
     */
    public abstract AbstractAgent clone(String name) throws HaltCondition;

    /**
     * Triggers a behavior associated with the cell. The specific details of the
     * behavior are defined by the cell's class. As of 1/24/2014, only the
     * BehaviorAgent class will have an instantiated trigger(...) method; other
     * classes of nanoverse.runtime.cells will throw an UnsupportedOperationException.
     *
     * @param behaviorName the name of the behavior to trigger.
     * @param caller       the coordinate of the triggering site. If invoked from a
     */
    public abstract void trigger(String behaviorName, Coordinate caller) throws HaltCondition;

    public abstract void die();
}
