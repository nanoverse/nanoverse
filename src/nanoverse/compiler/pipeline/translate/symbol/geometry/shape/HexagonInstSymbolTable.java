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

package nanoverse.compiler.pipeline.translate.symbol.geometry.shape;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.geometry.shape.HexagonLoader;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.runtime.geometry.shape.Hexagon;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class HexagonInstSymbolTable extends ShapeInstSymbolTable<Hexagon> {
    @Override
    public String getDescription() {
        return "A hexagon is a 2D boundary with three sets of parallel " +
            "sides, all equidistant from a center point. Requires a " +
            "Triangular lattice.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        radius(ret);
        return (ret);
    }

    private void radius(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "radius", "hexagon. Radius=0 corresponds to a single point");
    }

    @Override
    public Loader getLoader() {
        return new HexagonLoader();
    }
}
