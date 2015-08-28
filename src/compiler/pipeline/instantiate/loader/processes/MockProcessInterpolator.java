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

package compiler.pipeline.instantiate.loader.processes;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import layers.LayerManager;
import processes.BaseProcessArguments;

import java.util.Random;

/**
 * Created by dbborens on 8/26/2015.
 */
public class MockProcessInterpolator extends ProcessInterpolator {

    private final MockProcessDefaults defaults;

    public MockProcessInterpolator() {
        super();
        defaults = new MockProcessDefaults();
    }

    public MockProcessInterpolator(LoadHelper load,
                                   BaseProcessArgumentsLoader bpaLoader,
                                   MockProcessDefaults defaults) {

        super(load, bpaLoader);
        this.defaults = defaults;
    }

    public String identifier(MapObjectNode node) {
        return load.aString(node, "identifier");
    }

    public int count(MapObjectNode node, Random random) {
        return load.anInteger(node, "count", random, defaults::count);
    }

    public double weight(MapObjectNode node, Random random) {
        return load.aDouble(node, "weight", random, defaults::weight);
    }
}
