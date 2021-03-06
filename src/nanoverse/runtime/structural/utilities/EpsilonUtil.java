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

package nanoverse.runtime.structural.utilities;

public abstract class EpsilonUtil {

    private final static double epsilon = calcEpsilon();
    private final static float floatEpsilon = calcFloatEpsilon();

    private static double calcEpsilon() {
        double eps = 1.0D;

        while ((1.0 + (eps / 2.0)) != 1.0) {
            eps /= 2.0;
        }

        return eps;
    }

    private static float calcFloatEpsilon() {
        float eps = 1.0F;

        while ((1.0F + (eps / 2.0F)) != 1.0F) {
            eps /= 2.0F;
        }

        return eps;
    }

    public static boolean epsilonEquals(double p, double q) {
        double delta = p - q;
        double magnitude = Math.abs(delta);
        return (magnitude < epsilon());
    }

    public static double epsilon() {
        return epsilon;
    }

    public static float floatEpsilon() {
        return floatEpsilon;
    }
}
