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
import nanoverse.compiler.pipeline.instantiate.loader.geometry.shape.CuboidLoader;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.runtime.geometry.shape.Cuboid;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class CuboidInstSymbolTable extends ShapeInstSymbolTable<Cuboid> {
    @Override
    public String getDescription() {
        return "A cuboid is a 3D boundary with three sets of parallel sides.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        height(ret);
        width(ret);
        depth(ret);
        return (ret);
    }

    private void depth(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "depth", "cuboid");
    }

    private void width(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "width", "cuboid");
    }

    private void height(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "height", "cuboid");
    }

    @Override
    public Loader getLoader() {
        return new CuboidLoader();
    }
}
