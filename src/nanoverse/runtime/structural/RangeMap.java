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

package nanoverse.runtime.structural;

import nanoverse.runtime.structural.utilities.EpsilonUtil;

import java.util.ArrayList;

/**
 * A weighted collection of items of class T. Each T has some weight associated
 * with it. The total weight of the system is the sum of these weights. The
 * order of the items is identical to that in which they were loaded.
 * <p>
 * For example, I might load Foo with weight 0.5, and Bar with weight 1.0. That
 * would mean that weight range 0 <= weight < 0.5 corresponds to foo, and
 * 0.5 <= weight < 1.5 corresponds to bar.
 * <p>
 * After the collection is declared final (using the close() method), the user
 * may sample the collection by invoking the selectTarget(...) method, which
 * will retrieve items by range in the manner described above.
 * <p>
 * One would like
 */
public class RangeMap<T> {


    //private HashMap<T, Double> weights;

    // We track the floor of each element. It might seem easier to use, eg,
    // a HashMap, but we want order to be preserved and for items to be
    // loaded sequentially.

    // The first element is pre-loaded as 0.0, but each ceiling is then
    // appended. This means that floors has one too many elements, which is
    // handled accordingly.
    protected ArrayList<Double> floors;

    // We track the value associated with each floor in the same order as
    // the floors. Note that keys should always be one element shorter than
    // floors.

    protected ArrayList<T> keys;

    public RangeMap(int initialSize) {
        floors = new ArrayList<>(initialSize + 1);
        keys = new ArrayList<>(initialSize);
        floors.add(0.0);
    }

    public RangeMap() {
        //weights = new HashMap<>();
        floors = new ArrayList<>();
        keys = new ArrayList<>();
        floors.add(0.0);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RangeMap)) {
            return false;
        }

        RangeMap other = (RangeMap) obj;

        if (!EpsilonUtil.epsilonEquals(getTotalWeight(), other.getTotalWeight())) {
            return false;
        }


        if (!contentsEqual(this, other)) {
            return false;
        }

        return true;
    }

    public double getTotalWeight() {

        if (floors.size() == 1) {
            return 0.0;
        }

        double totalWeight = 0;
        for (int i = 0; i < floors.size() - 1; i++) {
            double weight = floors.get(i + 1) - floors.get(i);
            totalWeight += weight;
        }

        return totalWeight;
    }

    /**
     * Returns true if each weight range in each map returns equal objects for
     * the same value of x.
     * <p>
     * One would like to use a simpler method of comparing maps, say by asking
     * whether each key is contained in both maps. However, as
     * this class is generic, one is instead forced to compare element by
     * element.
     *
     * @return
     */
    private boolean contentsEqual(RangeMap p, RangeMap q) {
        // If they have a different number of elements, we already know that
        // they are unequal.
        if (p.floors.size() != q.floors.size()) {
            return false;
        }

        // Test each subsequent bin by its midpoint value.
        // The -1 is because the variable floors ends with the last ceiling added,
        // but it is supposed to be a list of floors.
        for (int i = 0; i < floors.size() - 1; i++) {
            double range = floors.get(i + 1) - floors.get(i);
            double floor = floors.get(i);
            double midpoint = floor + (range / 2.0);

            if (!binsEqual(p, q, midpoint)) {
                return false;
            }
        }

        return true;
    }

    protected boolean binsEqual(RangeMap p, RangeMap q, double midpoint) {
        Object pResult = p.selectTarget(midpoint);
        Object qResult = q.selectTarget(midpoint);

        if (!pResult.equals(qResult)) {
            return false;
        }

        return true;
    }

    /**
     * Returns a particular process based on an input between 0
     * and the total weight, with nanoverse.runtime.processes sorted numerically
     * by ID number.
     * <p>
     * So if w_i is the weight of process i, then you'll get back
     * <p>
     * 0 <= x < w_1         --> return process 1's id
     * w_1 <= x < w_1 + w_2 --> return process 2's id
     * ...
     * w_n-1 <= x < sum(w)  --> return process n's id
     *
     * @return
     */
    public T selectTarget(double x) {

        if (x < 0 || x > getTotalWeight()) {
            throw new IllegalStateException("Attempted to search range map beyond bounds");
        }

        RangeSearchHelper helper = new RangeSearchHelper(floors);

        Integer target = helper.findKey(x);

        T ret = keys.get(target);

        return ret;
    }

    @Override
    public RangeMap<T> clone() {
        int n = keys.size();
        RangeMap<T> cloned = new RangeMap(n);

        for (int i = 1; i < floors.size(); i++) {
            T key = keys.get(i - 1);
            Double weight = floors.get(i);

            cloned.add(key, weight);
        }

        return cloned;
    }

    public void add(T token, double weight) {
        keys.add(token);
        appendBin(weight);
//        weights.put(token, weight);

    }

    private void appendBin(double weight) {
        int n = floors.size();
        double lastBin = floors.get(n - 1);
        floors.add(lastBin + weight);
    }

    /**
     * Return the total number of bins (ranges) in the range map.
     *
     * @return
     */
    public int getNumBins() {
        // The first bin is a dummy, so we want one fewer
        return floors.size() - 1;
    }

    public ArrayList<T> getKeys() {
        return new ArrayList<>(keys);
    }
}
