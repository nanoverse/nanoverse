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

package compiler.pipeline.translate.symbol.layers.continuum;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.layers.LayerInstSymbolTable;
import compiler.pipeline.translate.symbol.primitive.booleans.BooleanClassSymbolTable;
import layers.continuum.ContinuumLayer;

import java.util.HashMap;

/**
 * Created by dbborens on 7/28/2015.
 */
public class ContinuumLayerInstSymbolTable extends LayerInstSymbolTable<ContinuumLayer> {
    @Override
    public String getDescription() {
        return "A continuum layer associates each lattice site with a " +
                "continuous numerical value. This value can be changed " +
                "directly, or transformed using a matrix operation.";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        disableOperators(ret);
        solver(ret);
        return ret;
    }

    public void solver(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new ContinuumSolverClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The method by which the continuum state should be updated (solved).");
        ret.put("solver", ms);
    }

    public void disableOperators(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new BooleanClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "If true, no matrix " +
                "operations may be scheduled for the simulation. If matrix " +
                "operations are not needed, disabling them can speed up " +
                "simulation updates dramatically.");
        ret.put("disableOperators", ms);
    }

    @Override
    public Loader getLoader() {
        return null;
    }
}
