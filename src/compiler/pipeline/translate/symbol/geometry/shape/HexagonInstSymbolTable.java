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

package compiler.pipeline.translate.symbol.geometry.shape;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.instantiate.loader.geometry.shape.HexagonLoader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.MemberSymbol;
import geometry.shape.Hexagon;

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
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        radius(ret);
        return(ret);
    }

    private void radius(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "radius", "hexagon. Radius=0 corresponds to a single point");
    }

    @Override
    public Loader getLoader() {
        return new HexagonLoader();
    }
}
