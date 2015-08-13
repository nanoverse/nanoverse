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

package layers.cell;

import geometry.Geometry;
import geometry.boundaries.HaltBoundary;
import layers.Layer;
import structural.annotations.FactoryTarget;

/**
 * @author David Bruce Borenstein
 * @test CellIntegrationTest
 */
public class CellLayer extends Layer {

    protected CellLayerContent content;

    @FactoryTarget
    public CellLayer(Geometry geom) {
        geometry = geom;
        reset();
    }

    public CellLookupManager getLookupManager() {
        return new CellLookupManager(geometry, content);
    }

    public CellUpdateManager getUpdateManager() {
        return new CellUpdateManager(content);
    }

    public CellLayerViewer getViewer() {
        return new CellLayerViewer(content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellLayer cellLayer = (CellLayer) o;

        if (content != null ? !content.equals(cellLayer.content) : cellLayer.content != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public CellLayer clone() {
        CellLayerContent contentClone = content.clone();
        CellLayer clone = new CellLayer(geometry);
        clone.content = contentClone;
        return clone;
    }

    @Override
    public String getId() {
        return "0";
    }

    @Override
    public void reset() {
        CellLayerIndices indices = new CellLayerIndices();

        // Oh man, do I hate the following two lines and the
        // "getComponentClasses" cloodge that makes them possible
        Class boundaryClass = geometry.getComponentClasses()[2];
        if (HaltBoundary.class.isAssignableFrom(boundaryClass)) {
            content = new HaltCellLayerContent(geometry, indices);
        } else if (geometry.isInfinite()) {
            content = new InfiniteCellLayerContent(geometry, indices);
        } else {
            content = new FiniteCellLayerContent(geometry, indices);
        }
    }
}
