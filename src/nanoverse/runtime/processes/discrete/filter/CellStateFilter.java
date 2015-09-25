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

package nanoverse.runtime.processes.discrete.filter;

import nanoverse.runtime.cells.Cell;
import nanoverse.runtime.control.arguments.*;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.CellLayer;
import nanoverse.runtime.layers.cell.CellLayerViewer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dbborens on 5/5/14.
 */
public class CellStateFilter extends Filter {

    private CellLayer layer;

    private IntegerArgument toChoose;

    /**
     * @param toChoose The cell state to retain. If random, a value will be
     *                 chosen each time the filter is applied.
     */
    @FactoryTarget
    public CellStateFilter(CellLayer layer, IntegerArgument toChoose) {
        this.toChoose = toChoose;
        this.layer = layer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellStateFilter that = (CellStateFilter) o;

        if (toChoose != null ? !toChoose.equals(that.toChoose) : that.toChoose != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = layer != null ? layer.hashCode() : 0;
        result = 31 * result + (toChoose != null ? toChoose.hashCode() : 0);
        return result;
    }

    @Override
    public List<Coordinate> apply(List<Coordinate> toFilter) {
        ArrayList<Coordinate> toRetain = getRetained(toFilter);
        return toRetain;
    }

    private ArrayList<Coordinate> getRetained(Collection<Coordinate> toFilter) {
        int chosen;
        try {
            chosen = toChoose.next();
        } catch (HaltCondition ex) {
            throw new IllegalStateException(ex);
        }

        ArrayList<Coordinate> toRetain = new ArrayList<>();
        CellLayerViewer viewer = layer.getViewer();
        for (Coordinate c : toFilter) {
            if (!viewer.isOccupied(c)) {
                continue;
            }

            Cell cell = layer.getViewer().getCell(c);

            if (cell.getState() == chosen) {
                toRetain.add(c);
            }
        }

        return toRetain;
    }
}
