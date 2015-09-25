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

package nanoverse.runtime.agent.targets;

import nanoverse.runtime.cells.BehaviorCell;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.discrete.filter.Filter;

import java.util.*;

/**
 * Targets specify which nanoverse.runtime.cells should receive the consequences
 * of an Action.
 * <p>
 * NOTE: Do not confuse 'callback' and 'caller.' The callback
 * is the cell associated with this Targeter object (i.e., whose
 * behavior is being described.) The caller is the cell that
 * triggered the behavior, if any.
 * <p>
 * Created by dbborens on 2/7/14.
 */
public abstract class TargetRule {

    protected Random random;
    protected int maximum;
    protected BehaviorCell callback;
    protected LayerManager layerManager;
    protected Filter filter;

    /**
     * @param callback     The cell whose behavior is being described
     * @param layerManager
     */
    public TargetRule(BehaviorCell callback, LayerManager layerManager, Filter filter, int maximum, Random random) {
        this.callback = callback;
        this.layerManager = layerManager;
        this.maximum = maximum;
        this.random = random;
        this.filter = filter;
    }

    public int getMaximum() {
        return maximum;
    }

    /**
     * Returns the list of target coordinates that satisfy
     * the action's target descriptor.
     *
     * @param caller The cell that triggered the action.
     */
    public List<Coordinate> report(BehaviorCell caller) {
        List<Coordinate> candidates = getCandidates(caller);
        List<Coordinate> filtered = filter.apply(candidates);
        List<Coordinate> targets = respectMaximum(filtered);
        return targets;
    }

    private List<Coordinate> respectMaximum(List<Coordinate> candidates) {
        // If maximum is < 0, it means that there is no maximum; return all.
        if (maximum < 0) {
            return candidates;
        }
        // If there the number of candidates does not exceed the max, return.
        if (candidates.size() <= maximum) {
            return candidates;
        }

        // Otherwise, permute and choose the first n, where n = maximum.
        Collections.shuffle(candidates, random);

        List<Coordinate> reduced = candidates.subList(0, maximum);

        return reduced;
    }

    protected abstract List<Coordinate> getCandidates(BehaviorCell caller);

    @Override
    /**
     * Targeting rules are equal if and only if they are of the
     * same class.
     */
    public boolean equals(Object obj) {
        Class objClass = obj.getClass();
        Class myClass = getClass();

        // Must be same class of targeting rule.
        if (!objClass.equals(myClass)) {
            return false;
        }

        // Other targeting rule must have same maximum.
        TargetRule other = (TargetRule) obj;
        if (other.maximum != this.maximum) {
            return false;
        }

        if (!this.filter.equals(other.filter)) {
            return false;
        }

        return true;
    }

    public abstract TargetRule clone(BehaviorCell child);

    public BehaviorCell getCallback() {
        return callback;
    }
}
