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

package nanoverse.compiler.pipeline.translate.symbol.control.run;

import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.instantiate.loader.control.*;
import nanoverse.compiler.pipeline.instantiate.loader.io.serialize.OutputManagerLoader;
import nanoverse.compiler.pipeline.instantiate.loader.layers.LayerManagerLoader;
import nanoverse.compiler.pipeline.translate.symbol.*;
import nanoverse.compiler.pipeline.translate.symbol.control.ParametersClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.geometry.GeometryDescriptorClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.io.serialize.OutputClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.layers.LayerClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.primitive.strings.StringClassSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.processes.ProcessClassSymbolTable;
import nanoverse.runtime.control.ProcessManager;
import nanoverse.runtime.control.run.Runner;
import nanoverse.runtime.io.serialize.SerializationManager;
import nanoverse.runtime.layers.LayerManager;

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
        ListSymbolTable<ProcessManager> lst = new ListSymbolTable<>(cst, ProcessManagerLoader::new);
        MemberSymbol ms = new MemberSymbol(lst, "A list of top-down nanoverse.runtime.processes" +
            " to perform at the outset of the simulation.");
        ret.put("processes", ms);
    }

    private void geometry(HashMap<String, MemberSymbol> ret) {
        GeometryDescriptorClassSymbolTable st = new GeometryDescriptorClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(st, "A description of the system's" +
            " top-level nanoverse.runtime.geometry.");
        ret.put("geometry", ms);
    }

    private void parameters(HashMap<String, MemberSymbol> ret) {
        ParametersClassSymbolTable st = new ParametersClassSymbolTable();
        MemberSymbol ms = new MemberSymbol(st, "System-level parameters.");
        ret.put("parameters", ms);
    }

    private void output(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new OutputClassSymbolTable();
        ListSymbolTable<SerializationManager> lst = new ListSymbolTable<>(cst, OutputManagerLoader::new);
        MemberSymbol ms = new MemberSymbol(lst, "A list of the simulation's" +
            "expected visualizations and reports.");
        ret.put("output", ms);
    }

    private void layerManager(HashMap<String, MemberSymbol> ret) {
        ClassSymbolTable cst = new LayerClassSymbolTable();
        ListSymbolTable<LayerManager> lst = new ListSymbolTable<>(cst, LayerManagerLoader::new);
        MemberSymbol ms = new MemberSymbol(lst, "A list of the simulation's" +
            "topological nanoverse.runtime.layers.");
        ret.put("layers", ms);
    }

    @Override
    public Loader getLoader() {
        return new ProjectLoader();
    }
}