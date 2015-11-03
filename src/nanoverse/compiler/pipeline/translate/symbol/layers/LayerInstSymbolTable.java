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

package nanoverse.compiler.pipeline.translate.symbol.layers;

import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.geometry.boundary.BoundaryClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.layers.Layer;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public abstract class LayerInstSymbolTable<T extends Layer> extends MapSymbolTable<T> {
    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        boundary(ret);
        id(ret);
        return ret;
    }

    private void id(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "A unique name for the layer.");
        ret.put("id", ms);
    }

    private void boundary(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BoundaryClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The boundary condition for this layer.");
        ret.put("boundary", ms);
    }
}
