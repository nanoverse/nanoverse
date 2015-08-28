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

package compiler.pipeline.instantiate.loader.processes.temporal;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.instantiate.loader.processes.*;
import compiler.pipeline.instantiate.loader.processes.discrete.*;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.arguments.DoubleArgument;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class TickInterpolator extends ProcessInterpolator {
    private final TickDefaults defaults;

    public TickInterpolator() {
        super();
        defaults = new TickDefaults();
    }

    public TickInterpolator(LoadHelper load,
                                      BaseProcessArgumentsLoader bpaLoader,
                                      TickDefaults defaults) {
        super(load, bpaLoader);
        this.defaults = defaults;
    }

    public DoubleArgument dt(MapObjectNode node, Random random) {
        return load.aDoubleArgument(node, "dt", random, defaults::dt);
    }
}
