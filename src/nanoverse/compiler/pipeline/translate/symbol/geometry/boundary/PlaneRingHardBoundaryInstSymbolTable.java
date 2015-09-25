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
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.PlaneRingHardBoundaryLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.geometry.boundaries.PlaneRingHard;

/**
 * Created by dbborens on 7/29/2015.
 */
public class PlaneRingHardBoundaryInstSymbolTable extends MapSymbolTable<PlaneRingHard> {
    @Override
    public String getDescription() {
        return "The PlaneRingHard boundary is a 2D boundary for AGENT nanoverse.runtime.layers " +
                "only. It has hard boundary conditions at top and bottom, " +
                "and periodic boundary conditions left and right. For " +
                "equivalent behavior in CONTINUUM nanoverse.runtime.layers, see the " +
                "PlaneRingReflecting boundary.";
    }

    @Override
    public Loader getLoader() {
        return new PlaneRingHardBoundaryLoader();
    }
}
