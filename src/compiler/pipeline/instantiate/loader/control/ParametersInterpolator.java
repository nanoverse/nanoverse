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

package compiler.pipeline.instantiate.loader.control;

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.*;

import java.util.Random;

/**
 * Created by dbborens on 8/14/2015.
 */
public class ParametersInterpolator {

    private final LoadHelper load;
    private final ParametersDefaults defaults;

    public ParametersInterpolator() {
        load = new LoadHelper();
        defaults = new ParametersDefaults();
    }

    public ParametersInterpolator(LoadHelper load,
                                  ParametersDefaults defaults) {

        this.load = load;
        this.defaults = defaults;
    }

    public long randomSeed(MapObjectNode node) {
        String seedStr = load.aString(node, "seed", defaults::randomSeed)
                .trim();


        // "*" -- use system time.
        if (seedStr.equals("*")) {
            return System.currentTimeMillis();
        }

        // Numeric value -- use that.
        return Long.valueOf(seedStr);
    }

    public int maxStep(MapObjectNode node, Random random) {
        return load.anInteger(node, "maxStep", random,
                defaults::maxStep);
    }

    public int instances(MapObjectNode node, Random random) {
        return load.anInteger(node, "instances", random,
                defaults::instances);
    }

    public String path(MapObjectNode node) {
        return load.aString(node, "path", defaults::path);
    }

    public String project(MapObjectNode node) {
       return load.aString(node, "project", defaults::project);
    }

    public boolean date(MapObjectNode node, Random random) {
        return load.aBoolean(node, "date", random, defaults::date);
    }

    public Random random(long randomSeed) {
        return new Random(randomSeed);
    }
}