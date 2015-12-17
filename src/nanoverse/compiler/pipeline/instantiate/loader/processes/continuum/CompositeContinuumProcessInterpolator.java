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

package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.error.UserError;
import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.processes.*;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.compiler.pipeline.translate.symbol.InstantiableSymbolTable;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.*;
import nanoverse.runtime.processes.NanoverseProcess;
import nanoverse.runtime.processes.continuum.ContinuumProcess;

import java.util.stream.Stream;

/**
 * Created by dbborens on 12/17/2015.
 */
public class CompositeContinuumProcessInterpolator extends ProcessInterpolator {

    private final CompositeContinuumProcessDefaults defaults;

    public CompositeContinuumProcessInterpolator() {
        super();
        this.defaults = new CompositeContinuumProcessDefaults();
    }

    public CompositeContinuumProcessInterpolator(LoadHelper load, BaseProcessArgumentsLoader bpaLoader,
                                                 CompositeContinuumProcessDefaults defaults) {
        super(load, bpaLoader);
        this.defaults = defaults;
    }

    public Stream<ContinuumProcess> processes(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        if (node == null || !node.hasMember("children")) {
            return defaults.processes();
        }

        ListObjectNode children = (ListObjectNode) node.getMember("children");
        return children.getMemberStream()
            .map(cNode -> (MapObjectNode) cNode)
            .map(cNode -> toProcess(cNode, lm, p));
    }

    private ContinuumProcess toProcess(MapObjectNode cNode, LayerManager lm, GeneralParameters p) {
        InstantiableSymbolTable ist = cNode.getSymbolTable();
        ProcessLoader loader = (ProcessLoader) ist.getLoader();
        NanoverseProcess process = loader.instantiate(cNode, lm, p);
        if (!(process instanceof ContinuumProcess)) {
            throw new UserError("Unexpected child process '" +
                process.getClass().getSimpleName() +
                "' in composite continuum process", cNode.getLineNumber());
        }
        return (ContinuumProcess) process;
    }

    public String layer(MapObjectNode node) {
        return load.aString(node, "layer");
    }

    public ContinuumLayerScheduler scheduler(LayerManager lm, String layerId) {
        ContinuumLayer layer = lm.getContinuumLayer(layerId);
        return layer.getScheduler();
    }
}
