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
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.MemberSymbol;
import geometry.shape.Rectangle;

import java.util.HashMap;

/**
 * Created by dbborens on 7/25/2015.
 */
public class RectangleInstSymbolTable extends ShapeInstSymbolTable<Rectangle> {
    @Override
    public String getDescription() {
        return "A rectangle is a 2D boundary with two sets of parallel sides.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        height(ret);
        width(ret);
        return(ret);
    }

    private void width(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "width", "rectangle");
    }

    private void height(HashMap<String, MemberSymbol> ret) {
        loadDimension(ret, "height", "rectangle");
    }

    @Override
    public Loader getLoader(ObjectNode node) {
        return null;
    }
}
