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
import nanoverse.compiler.pipeline.instantiate.loader.geometry.boundary.ArenaBoundaryLoader;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.runtime.geometry.boundaries.Arena;

/**
 * Created by dbborens on 7/29/2015.
 */
public class ArenaBoundaryInstSymbolTable extends MapSymbolTable<Arena> {
    @Override
    public String getDescription() {
        return "The Arena boundary is for AGENT nanoverse.runtime.layers only. It allows " +
                "agents to drift over the boundary while resolving an event, " +
                "but any nanoverse.runtime.agent that ends an event over the boundary is " +
                "removed from the system. For the equivalent behavior in a " +
                "CONTINUUM layer, see the Absorbing boundary.";
    }

    @Override
    public Loader getLoader() {
        return new ArenaBoundaryLoader();
    }
}
