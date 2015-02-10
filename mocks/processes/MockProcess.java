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

package processes;

import control.arguments.ConstantInteger;
import control.halt.HaltCondition;
import layers.MockLayerManager;
import processes.gillespie.GillespieState;

/**
 * Dummy process that does nothing except report that it
 * was invoked. Used for testing.
 *
 * @author David Bruce Borenstein
 */
public class MockProcess extends EcoProcess {

    private Integer count;
    private Double weight;

    // Some identifier to let the user distinguish between null processes
    private String identifier;

    // Tells the user how many times the process was invoked. Useful for
    // testing.
    private int timesFired = 0;

    public MockProcess(BaseProcessArguments arguments, String identifier, double weight, int count) {
        super(arguments);
        this.count = count;
        this.weight = weight;
        this.identifier = identifier;
    }

    public MockProcess() {
        super(new BaseProcessArguments(null, null, 0, new ConstantInteger(0), new ConstantInteger(1)));
    }

    @Override
    public void init() {
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public void target(GillespieState gs) throws HaltCondition {
        if (gs != null) {
            gs.add(getID(), count, weight);
        }
    }

    @Override
    public void fire(StepState state) throws HaltCondition {
        timesFired++;
        System.out.println("   Fired null event " + getID() + ".");
    }

    public int getTimesFired() {
        return timesFired;
    }

    public void setTimesFired(int timesFired) {
        this.timesFired = timesFired;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setLayerManager(MockLayerManager layerManager) {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockProcess that = (MockProcess) o;

        if (count != null ? !count.equals(that.count) : that.count != null)
            return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null)
            return false;
        if (weight != null ? !weight.equals(that.weight) : that.weight != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = count != null ? count.hashCode() : 0;
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }
}
