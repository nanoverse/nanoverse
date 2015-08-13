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

import control.GeneralParameters;
import control.arguments.*;
import control.halt.HaltCondition;
import processes.gillespie.GillespieState;
import test.EslimeTestCase;

/**
 * Test to make sure that stochastic values work as expected in a process.
 * This is a regression test for a possible failure mode observed in April 2014.
 * <p>
 * Created by dbborens on 5/1/14.
 */
public class StochasticProcessTest extends EslimeTestCase {


    protected static double mean(double[] a) {
        if (a.length == 0) return Double.NaN;
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i];
        }
        return sum / a.length;
    }

    public void testOngoing() throws Exception {
        double[] obs = new double[100];
        GeneralParameters p = makeMockGeneralParameters();
        UniformInteger ongoing = new UniformInteger(408, 410, p.getRandom());
        StochasticProcess query = new StochasticProcess(ongoing);
        for (int i = 0; i < 100; i++) {
            query.target(null);
            obs[i] = query.getOngoingVal();
        }

        examine(obs);

    }

    private void examine(double[] results) {
        assertEquals(408.0, minValue(results), epsilon);
        assertEquals(409.0, maxValue(results), epsilon);
        double expected = 408.5;
        double actual = mean(results);
        double var = (1.0 / 12.0) * Math.pow(1, 2.0);
        assertEquals(expected, actual, var);
    }

    private double minValue(double[] a) {
        double cur = Double.MAX_VALUE;

        for (double x : a) {
            if (x < cur) {
                cur = x;
            }
        }

        return cur;
    }

    private double maxValue(double[] a) {
        double cur = Double.MIN_VALUE;

        for (double x : a) {
            if (x > cur) {
                cur = x;
            }
        }

        return cur;
    }

    private class StochasticProcess extends NanoverseProcess {

        private Argument<Integer> ongoing;
        private Integer ongoingVal;

        public StochasticProcess(Argument<Integer> ongoing) {
            super(makeBaseProcessArguments(null, null));
            this.ongoing = ongoing;
        }

        @Override
        public void init() {
        }

        @Override
        public void target(GillespieState gs) throws HaltCondition {
            ongoingVal = ongoing.next();
        }

        public double getOngoingVal() {
            return ongoingVal;
        }

        @Override
        public void fire(StepState state) throws HaltCondition {

        }

    }

}
