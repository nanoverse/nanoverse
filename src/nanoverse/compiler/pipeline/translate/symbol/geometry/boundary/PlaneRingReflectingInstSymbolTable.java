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

package nanoverse.compiler.pipeline.translate.symbol.geometry.boundary;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.PlaneRingReflectingBoundaryLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.geometry.boundaries.PlaneRingReflecting;

/**
 * Created by dbborens on 7/29/2015.
 */
public class PlaneRingReflectingInstSymbolTable extends MapSymbolTable<PlaneRingReflecting> {
    @Override
    public String getDescription() {
        return "The PlaneRingReflecting boundary is a 2D boundary for " +
                "CONTINUUM nanoverse.runtime.layers only. It has reflecting boundary " +
                "conditions top and bottom, and periodic boundaries left and " +
                "right. For equivalent boundary conditions in AGENT nanoverse.runtime.layers, " +
                "see the PlaneRingHard boundary.";
    }

    @Override
    public Loader getLoader() {
        return new PlaneRingReflectingBoundaryLoader();
    }
}
