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

package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.processes.*;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import nanoverse.runtime.processes.continuum.*;
import no.uib.cipr.matrix.sparse.CompDiagMatrix;

import java.util.Random;
import java.util.function.Consumer;

/**
 * Created by dbborens on 8/26/2015.
 */
public class DiffusionProcessInterpolator extends ProcessInterpolator {
    public DiffusionProcessInterpolator() {
        super();
    }

    public DiffusionProcessInterpolator(LoadHelper load, BaseProcessArgumentsLoader bpaLoader) {
        super(load, bpaLoader);
    }

    public String layer(MapObjectNode node) {
        return load.aString(node, "layer");
    }

    public double constant(MapObjectNode node, Random random) {
        return load.aDouble(node, "constant", random);
    }

    public DiffusionOperator operator(String layer, double constant, LayerManager lm) {
        Geometry g = lm.getContinuumLayer(layer).getGeometry();
        int connectivity = g.getConnectivity();
        int dimensionality = g.getDimensionality();
        DiffusionConstantHelper helper = new DiffusionConstantHelper(constant,
            connectivity,
            dimensionality);

        return new DiffusionOperator(helper, g);
    }

    public Consumer<CompDiagMatrix> target(String layer, LayerManager lm) {
        ContinuumLayer cl = lm.getContinuumLayer(layer);
        Consumer<CompDiagMatrix> target = matrix -> cl
            .getScheduler()
            .apply(matrix);

        return target;
    }
}
