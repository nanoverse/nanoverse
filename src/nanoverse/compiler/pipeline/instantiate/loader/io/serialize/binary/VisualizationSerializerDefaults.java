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

package nanoverse.compiler.pipeline.instantiate.loader.io.serialize.binary;

import nanoverse.compiler.pipeline.instantiate.loader.io.visual.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.visual.Visualization;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.NotYetImplementedException;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationSerializerDefaults {
    public String prefix() {
        return "img";
    }

    public Visualization visualization(LayerManager layerManager, GeneralParameters p) {
        Geometry geometry = layerManager.getAgentLayer().getGeometry();

        if (geometry.getDimensionality() == 1) {
            KymographLoader loader = new KymographLoader();
            return loader.instantiate(layerManager, p);
        } else if (geometry.getDimensionality() == 2) {
            MapVisualizationLoader loader = new MapVisualizationLoader();
            return loader.instantiate(layerManager, p);
        } else {
        }
        throw new NotYetImplementedException();
    }
}
