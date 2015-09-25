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

package nanoverse.compiler.pipeline.instantiate.loader.control.identifiers;

import nanoverse.compiler.pipeline.instantiate.helpers.LoadHelper;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;

import java.util.Random;

/**
 * Created by dbborens on 9/19/2015.
 */
public class Coordinate3DInterpolator {

    private final LoadHelper load;
    private final Coordinate3DDefaults defaults;

    public Coordinate3DInterpolator() {
        load = new LoadHelper();
        defaults = new Coordinate3DDefaults();
    }

    public Coordinate3DInterpolator(LoadHelper load,
                                    Coordinate3DDefaults defaults) {
        this.load = load;
        this.defaults = defaults;
    }

    public int x(MapObjectNode node, Random random) {
        return load.anInteger(node, "x", random);
    }

    public int y(MapObjectNode node, Random random) {
        return load.anInteger(node, "y", random);
    }

    public int z(MapObjectNode node, Random random) {
        return load.anInteger(node, "z", random);
    }

    public int flags(MapObjectNode node, Random random) {
        return load.anInteger(node, "flags", random, defaults::flags);
    }
}
