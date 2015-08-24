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

package compiler.pipeline.instantiate.loader.io.visual;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import io.visual.VisualizationProperties;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/23/2015.
 */
public class KymographInterpolator {
    private final KymographDefaults defaults;
    private final VisualizationPropertiesLoader vpLoader;

    public KymographInterpolator() {
        defaults = new KymographDefaults();
        vpLoader = new VisualizationPropertiesLoader();
    }

    public KymographInterpolator(KymographDefaults defaults,
                                 VisualizationPropertiesLoader vpLoader) {
        this.defaults = defaults;
        this.vpLoader = vpLoader;
    }

    public VisualizationProperties properties(MapObjectNode node, GeneralParameters p) {
        if (node == null) {
            return defaults.properties(p);
        }

        return vpLoader.instantiate(node, p);
    }
}
