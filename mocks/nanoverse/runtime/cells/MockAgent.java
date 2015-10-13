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

import nanoverse.runtime.agent.BehaviorCell;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.utilities.EpsilonUtil;

/**
 * Mock cell class used for testing. We make it extend from BehaviorCell
 * for compatibility with BehaviorCell-only classes. (BehaviorCell is a
 * subclass of AbstractAgent which is capable of engaging in arbitrary behaviors,
 * which can then be used for nanoverse.runtime.agent-based modeling.)
 * <p>
 * Created by dbborens on 1/13/14.
 */
public class MockAgent extends BehaviorCell {

    private int considerCount;
    private MockAgent child;
    private int state = 1;
    private double health = 0.0;
    private double production;
    private String lastTriggeredBehaviorName;
    private Coordinate lastTriggeredCaller;
    private boolean divisible;
    private boolean died;
    private int triggerCount = 0;

    public MockAgent() {
        super();
    }

    public MockAgent(int state) {
        super();
        this.state = state;
    }

    @Override
    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public boolean isDivisible() {
        return divisible;
    }

    @Override
    public void setDivisible(boolean divisible) {
        this.divisible = divisible;
    }

    @Override
    public int consider() {
        considerCount++;
        return considerCount;
    }

    @Override
    public void apply() {
        considerCount = 0;
    }

    @Override
    public MockAgent divide() {
        return child;
    }

    @Override
    public MockAgent clone(int state) {
        return child;
    }

    @Override
    public double getProduction(String solute) {
        return production;
    }

    @Override
    public void adjustHealth(double delta) {

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
    public boolean equals(Object obj) {
        if (!(obj instanceof MockAgent)) {
            return false;
        }

        MockAgent other = (MockAgent) obj;

        if (other.state != this.state) {
            return false;
        }

        if (!EpsilonUtil.epsilonEquals(other.health, health)) {
            return false;
        }

        return true;
    }

    public void setProduction(double production) {
        this.production = production;
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

    public boolean died() {
        return died;
    }
}
