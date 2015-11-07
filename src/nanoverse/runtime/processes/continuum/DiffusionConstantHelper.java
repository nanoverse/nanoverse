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

package nanoverse.runtime.processes.continuum;

/**
 * The diffusion constant assumes a continuous space. Since both space and
 * time are discretized in Nanoverse, it is necessary to adjust the
 * diffusion constant.
 * <p>
 * In general,
 * <p>
 * n      dt
 * a = D * --- * ------
 * m    (dx)^2
 * <p>
 * Where "m" is the connectivity of the graph and "n" is the dimensionality
 * of the graph. We assume that dt and dx are both equal to unity, so the
 * DiffusionConstantHelper need only concern iself with D, n and m.
 * <p>
 * Created by dbborens on 1/24/15.
 */
public class DiffusionConstantHelper {

    private double neighborValue;
    private double diagonalValue;

    public DiffusionConstantHelper(double baseConstant, int connectivity, int dimensionality) {
        if (baseConstant < 0.0) {
            throw new IllegalArgumentException("Illegal diffusion constant: Negative diffusion constant not permitted.");
        }

        neighborValue = baseConstant * ((double) dimensionality / (double) connectivity);
        diagonalValue = -1.0 * baseConstant * Math.pow(dimensionality, 2.0);

        if (diagonalValue < -1.0) {
            throw new IllegalArgumentException("Illegal diffusion constant: Total flux exceeds 100%.");
        }
    }

    /**
     * Return the fraction of the solute to be transferred to
     * each neighbor per dt.
     *
     * @return
     */
    public double getNeighborValue() {
        return neighborValue;
    }

    /**
     * Return the fraction of the solute to be retained at
     * the self-coordinate (diagonal) per dt.
     */
    public double getDiagonalValue() {
        return diagonalValue;
    }
}
