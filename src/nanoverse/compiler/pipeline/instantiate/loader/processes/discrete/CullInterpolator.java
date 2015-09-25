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

package nanoverse.compiler.pipeline.instantiate.loader.processes.discrete;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.instantiate.loader.processes.BaseProcessArgumentsLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class CullInterpolator extends DiscreteProcessInterpolator {
    private final CullDefaults defaults;

    public CullInterpolator() {
        super();
        defaults = new CullDefaults();
    }

    public CullInterpolator(LoadHelper load,
                            BaseProcessArgumentsLoader bpaLoader,
                            DiscreteProcessArgumentsLoader dpaLoader,
                            CullDefaults defaults) {
        super(load, bpaLoader, dpaLoader);
        this.defaults = defaults;
    }

    public double threshold(MapObjectNode node, Random random) {
        return load.aDouble(node, "threshold", random, defaults::threshold);
    }
}
