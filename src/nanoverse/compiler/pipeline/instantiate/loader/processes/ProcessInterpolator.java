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

package nanoverse.compiler.pipeline.instantiate.loader.processes;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.BaseProcessArguments;

/**
 * Created by dbborens on 8/26/2015.
 */
public class ProcessInterpolator {

    protected final LoadHelper load;

    private final BaseProcessArgumentsLoader bpaLoader;

    public ProcessInterpolator() {
        load = new LoadHelper();
        bpaLoader = new BaseProcessArgumentsLoader();
    }

    public ProcessInterpolator(LoadHelper load,
                               BaseProcessArgumentsLoader bpaLoader) {
        this.load = load;
        this.bpaLoader = bpaLoader;
    }

    public BaseProcessArguments arguments(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        return bpaLoader.instantiate(node, lm, p);
    }
}
