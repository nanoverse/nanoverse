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

package cells;

import control.halt.HaltCondition;
import control.identifiers.Coordinate;

/**
 * Parent class for cells.
 *
 * @author David Bruce Borenstein
 */
public abstract class Cell {
    private int state;
    private double health;
    private boolean divisible;

    public int getState() {
        return state;
    }

    protected void setState(int state) {
        if (state == 0) {
            throw new IllegalStateException("Attempted to assign special 'dead' state code 0 to an active cell.");
        }

        this.state = state;
    }

    public double getHealth() {
        return health;
    }

    protected void setHealth(double health) throws HaltCondition {
        this.health = health;
    }

    public boolean isDivisible() {
        return divisible;
    }

    protected void setDivisible(boolean divisible) throws HaltCondition {
        this.divisible = divisible;
    }

    public abstract int consider();

    /**
     * Applies changes to the cell. DO NOT CALL THIS METHOD DIRECTLY
     * FROM A PROCESS. Instead, use lattice.apply().
     */
    public abstract void apply();

    /**
     * Instructs the cell to divide and returns the daughter cell.
     * This triggers any division-related behaviors.
     * <p>
     * DO NOT CALL THIS METHOD DIRECTLY
     * FROM A PROCESS. Instead, use lattice.divide().
     *
     * @return
     */
    public abstract Cell divide() throws HaltCondition;

    /**
     * Creates an exact replica of the cell, differing only by the
     * state value. Copies all internal state variables. Does not
     * trigger any division-related events or changes.
     *
     * @param state
     * @return
     */
    public abstract Cell clone(int state) throws HaltCondition;

    /**
     * Returns the current production of the specified solute.
     *
     * @param solute the ID of the solute layer associated with
     *               the solute whose production is to be checked.
     * @return
     */
    public abstract double getProduction(String solute);

    public Cell replicate() throws HaltCondition {
        return clone(state);
    }

    /**
     * Informs the cell that it has been given a direct benefit.
     * The effect of this benefit depends on the cell class.
     */
    public abstract void adjustHealth(double delta) throws HaltCondition;

    /**
     * Triggers a behavior associated with the cell. The specific details of the
     * behavior are defined by the cell's class. As of 1/24/2014, only the
     * BehaviorCell class will have an instantiated trigger(...) method; other
     * classes of cells will throw an UnsupportedOperationException.
     *
     * @param behaviorName the name of the behavior to trigger.
     * @param caller       the coordinate of the triggering site. If invoked from a
     */
    public abstract void trigger(String behaviorName, Coordinate caller) throws HaltCondition;

    public abstract void die();
}
