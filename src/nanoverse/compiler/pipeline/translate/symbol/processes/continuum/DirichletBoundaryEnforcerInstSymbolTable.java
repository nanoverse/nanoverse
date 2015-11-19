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

package nanoverse.compiler.pipeline.translate.symbol.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.processes.continuum.DirichletBoundaryEnforcerLoader;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MapSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.MemberSymbol;
import nanoverse.compiler.pipeline.translate.symbol.ResolvingSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.geometry.set.CoordinateSetClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.doubles.DoubleClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.runtime.processes.continuum.DirichletBoundaryEnforcer;

import java.util.HashMap;

/**
 * Created by dbborens on 10/5/2015.
 */
public class DirichletBoundaryEnforcerInstSymbolTable extends ContinuumProcessInstSymbolTable<DirichletBoundaryEnforcer> {

    @Override
    public Loader getLoader() {
        return new DirichletBoundaryEnforcerLoader();
    }

    @Override
    public String getDescription() {
        return "A Dirichlet boundary condition describes region of space " +
                "within which the value of a field remains constant for all " +
                "time. This region is often placed along an edge of the " +
                "system, but it need not be. For example, one might hold " +
                "the value of the continuum constant in a region at the " +
                "center of the simulation space.\n\n" +
                "The DirichletBoundaryEnforcer sets the specified " +
                "coordinates to the desired value, regardless of any other " +
                "processes that might have otherwise affected them. It must " +
                "be applied between a Hold process and a Release process.";
    }

    @Override
    public HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = super.resolveMembers();
        activeSites(ret);
        layer(ret);
        value(ret);
        return ret;
    }

    public void value(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new DoubleClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Value at which to hold all " +
                "locations along this boundary.");
        ret.put("value", ms);
    }

    public void layer(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "Continuum layer with which " +
                "to associate this DBC.");
        ret.put("layer", ms);
    }

    public void activeSites(HashMap<String, MemberSymbol> ret) {
        ResolvingSymbolTable rst = new CoordinateSetClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(rst, "The set of coordinates to be " +
                "held at a constant value.");
        ret.put("activeSites", ms);
    }
}
