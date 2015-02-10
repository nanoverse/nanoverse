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

package layers.continuum;

import control.identifiers.Coordinate;
import no.uib.cipr.matrix.DenseVector;
import no.uib.cipr.matrix.Vector;

import java.util.function.Function;

/**
 * Created by dbborens on 12/26/14.
 */
public class ContinuumLayerContent {
    protected Vector state;

    // Map of coordinate --> vector index
    private Function<Coordinate, Integer> indexer;
    private int n;

    public ContinuumLayerContent(Function<Coordinate, Integer> indexer, int n) {
        this.indexer = indexer;
        this.n = n;
    }

    public double get(Coordinate c) {
        int index = indexer.apply(c);
        double ret = state.get(index);
        return ret;
    }

    public Vector getState() {
        return state;
    }

    public void setState(Vector state) {
        this.state = state;
    }

    public void reset() {
        this.state = new DenseVector(n);
    }

}
