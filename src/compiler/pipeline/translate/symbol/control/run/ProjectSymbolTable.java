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

package compiler.pipeline.translate.symbol.control.run;

import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.ObjectNode;
import compiler.pipeline.translate.symbol.*;
import compiler.pipeline.translate.symbol.control.ParametersClassSymbolTable;
import compiler.pipeline.translate.symbol.geometry.GeometryDescriptorClassSymbolTable;
import compiler.pipeline.translate.symbol.io.serialize.OutputClassSymbolTable;
import compiler.pipeline.translate.symbol.layers.LayerClassSymbolTable;
import compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import compiler.pipeline.translate.symbol.processes.ProcessClassSymbolTable;
import control.ProcessManager;
import control.run.Runner;
import io.serialize.SerializationManager;
import layers.LayerManager;

import java.util.HashMap;

/**
 * Created by dbborens on 7/21/2015.
 */
public class ProjectSymbolTable extends MapSymbolTable<Runner> {

    @Override
    public String getDescription() {
        return "The Project object represents a simulation or set of " +
                "simulations, along with any desired reporting, as well" +
                " as meta-instructions (such as running the simulation " +
                "more than once).";
    }

    @Override
    protected HashMap<String, MemberSymbol> resolveMembers() {
        HashMap<String, MemberSymbol> ret = new HashMap<>(5);

        layerManager(ret);
        output(ret);
        processes(ret);
        parameters(ret);
        geometry(ret);
        version(ret);
        return ret;
    }

    private void version(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new StringClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(cst, "The version of Nanoverse for " +
                "which this project was developed. The simulation will not " +
                "run unless the project version matches the Nanoverse " +
                "runtime version.");
        ret.put("version", ms);
    }

    private void processes(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new ProcessClassSymbolTable();
        ListSymbolTable<ProcessManager> lst = new ListSymbolTable<>(cst);
        MemberSymbol ms = new MemberSymbol(lst, "A list of top-down processes" +
            " to perform at the outset of the simulation.");
        ret.put("processes", ms);
    }

    private void geometry(HashMap<String, MemberSymbol> ret) {
        GeometryDescriptorClassSymbolTable st = new GeometryDescriptorClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(st, "A description of the system's" +
                " top-level geometry.");
        ret.put("geometry", ms);
    }

    private void parameters(HashMap<String, MemberSymbol> ret) {
        ParametersClassSymbolTable st = new ParametersClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(st, "System-level parameters.");
        ret.put("parameters", ms);
    }

    private void output(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new OutputClassSymbolTable();
        ListSymbolTable<SerializationManager> lst = new ListSymbolTable<>(cst);
        MemberSymbol ms = new MemberSymbol(lst, "A list of the simulation's" +
                "expected visualizations and reports.");
        ret.put("output", ms);
    }

    private void layerManager(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new LayerClassSymbolTable();
        ListSymbolTable<LayerManager> lst = new ListSymbolTable<>(cst);
        MemberSymbol ms = new MemberSymbol(lst, "A list of the simulation's" +
                "topological layers.");
        ret.put("layers", ms);
    }

    @Override
    public Loader getLoader() {
        return null;
    }
}
