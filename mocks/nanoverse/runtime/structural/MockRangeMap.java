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

package nanoverse.runtime.structural;

/**
 * Created by dbborens on 3/18/14.
 */
public class MockRangeMap<T> extends RangeMap<T> {

    private boolean reportEquality;
    private T nextTarget;
    private int timesCloned;

    public MockRangeMap() {
    }

    @Override
    public T selectTarget(double x) {
        return nextTarget;
    }

    /**
     * Returns true if and only if reportEquality is set to
     * true.
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return reportEquality;
    }

    @Override
    public RangeMap<T> clone() {
        timesCloned++;

        MockRangeMap<T> ret = new MockRangeMap<>();
        ret.setReportEquality(reportEquality);
        ret.setNextTarget(nextTarget);
        return ret;
    }

    /**
     * Specifies whether this object should report itself as equal
     * to whatever it happens to be compared to.
     */
    public void setReportEquality(boolean reportEquality) {
        this.reportEquality = reportEquality;
        timesCloned = 0;
    }

    /**
     * Specifies the next target to return, regardless
     * of what "x" is.
     *
     * @param nextTarget
     */
    public void setNextTarget(T nextTarget) {
        this.nextTarget = nextTarget;
    }

    public int getTimesCloned() {
        return timesCloned;
    }
}
