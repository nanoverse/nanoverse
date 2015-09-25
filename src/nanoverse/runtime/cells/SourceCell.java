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

import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.utilities.EpsilonUtil;

import java.util.HashMap;

/**
 * Created by dbborens on 1/2/14.
 */
public class SourceCell extends Cell {
    private int considerCount;
    private HashMap<String, Double> production;

    private double epsilon;

    public SourceCell(int state, HashMap<String, Double> production) throws HaltCondition {
        super();
        considerCount = 0;
        setHealth(0);
        setState(state);
        this.production = production;
        this.epsilon = EpsilonUtil.epsilon();
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
    public Cell divide() {
        throw new UnsupportedOperationException("Source nanoverse.runtime.cells cannot divide");
    }

    @Override
    public double getProduction(String solute) {
        return production.get(solute);
    }

    @Override
    public Cell clone(int state) throws HaltCondition {
        HashMap<String, Double> prodCopy = (HashMap<String, Double>) production.clone();
        SourceCell cc = new SourceCell(getState(), prodCopy);
        return cc;
    }

    @Override
    public void adjustHealth(double delta) {
        throw new UnsupportedOperationException("Source nanoverse.runtime.cells cannot eat");
    }

    @Override
    public void trigger(String behaviorName, Coordinate caller) throws HaltCondition {
        throw new UnsupportedOperationException("Behaviors are not supported in the SourceCell class.");
    }

    @Override
    public void die() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SourceCell)) {
            return false;
        }

        SourceCell other = (SourceCell) obj;

        if (other.getState() != this.getState()) {
            return false;
        }

        if (prodsUnequal(this, other)) {
            return false;
        }

        return true;
    }

    private boolean prodsUnequal(SourceCell p, SourceCell q) {
        if (p.production.size() != q.production.size()) {
            return true;
        }

        for (String s : p.production.keySet()) {
            if (!q.production.containsKey(s)) {
                return true;
            }

            double pProd = p.production.get(s);
            double qProd = q.production.get(s);

            double normDelta = Math.abs(pProd - qProd);

            if (normDelta > epsilon) {
                return true;
            }
        }

        return false;
    }
}
