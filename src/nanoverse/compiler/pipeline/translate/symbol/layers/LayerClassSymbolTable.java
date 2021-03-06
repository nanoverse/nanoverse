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
import nanoverse.compiler.pipeline.translate.symbol.layers.agent.AgentLayerInstSymbolTable;
import nanoverse.compiler.pipeline.translate.symbol.layers.continuum.ContinuumLayerInstSymbolTable;
import nanoverse.runtime.layers.Layer;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created by dbborens on 7/21/2015.
 */
public class LayerClassSymbolTable extends ClassSymbolTable<Layer> {


    @Override
    public String getDescription() {
        return "Simulation nanoverse.runtime.layers. Layers are topological, rather than " +
            "geometric, concepts: nanoverse.runtime.layers share coordinate systems and " +
            "consist of identical lattices, with a special relationship " +
            "between a given site (x, y) on each of the nanoverse.runtime.layers in a " +
            "simulation. Use separate nanoverse.runtime.layers to couple multiple nanoverse.runtime.agent-" +
            "based or numerical nanoverse.runtime.processes.";
    }

    @Override
    public HashMap<String, Supplier<InstantiableSymbolTable>> resolveSubclasses() {
        HashMap<String, Supplier<InstantiableSymbolTable>> ret = new HashMap<>();
        continuumLayer(ret);
        agentLayer(ret);
        return ret;
    }

    private void continuumLayer(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = ContinuumLayerInstSymbolTable::new;
        ret.put("ContinuumLayer", supplier);
    }

    private void agentLayer(HashMap<String, Supplier<InstantiableSymbolTable>> ret) {
        Supplier<InstantiableSymbolTable> supplier = AgentLayerInstSymbolTable::new;
        ret.put("AgentLayer", supplier);
    }
}
