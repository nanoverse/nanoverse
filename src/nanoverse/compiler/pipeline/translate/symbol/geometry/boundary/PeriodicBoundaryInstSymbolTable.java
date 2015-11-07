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

package nanoverse.compiler.pipeline.translate.symbol.geometry.boundary;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.PeriodicBoundaryLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.geometry.boundaries.Periodic;

/**
 * Created by dbborens on 7/29/2015.
 */
public class PeriodicBoundaryInstSymbolTable extends MapSymbolTable<Periodic> {
    @Override
    public String getDescription() {
        return "A periodic boundary wraps each edge around to a parallel " +
            "edge on the other side. It requires that the simulation " +
            "space have a shape with exactly twice the edges of its " +
            "dimension (ie, Line, Rectangle, Cuboid). Periodic " +
            "boundaries are compatible with both continuum and nanoverse.runtime.agent " +
            "nanoverse.runtime.layers.";
    }

    @Override
    public Loader getLoader() {
        return new PeriodicBoundaryLoader();
    }
}
