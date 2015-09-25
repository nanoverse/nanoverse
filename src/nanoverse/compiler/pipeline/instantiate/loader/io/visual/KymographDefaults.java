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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.VisualizationPropertiesFactory;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.VisualizationProperties;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/23/2015.
 */
public class KymographDefaults {
    public static final int DEFAULT_EDGE = 1;

    // You often don't want a cell outline for a kymograph. Default to 0.
    public static final int DEFAULT_OUTLINE = 0;

    public VisualizationProperties properties(GeneralParameters p) {
        VisualizationPropertiesLoader loader = new VisualizationPropertiesLoader();

        Stream<Consumer<VisualizationPropertiesFactory>> overrides = Stream.of(
            factory -> factory.setEdge(DEFAULT_EDGE),
            factory -> factory.setOutline(DEFAULT_OUTLINE)
        );

        return loader.instantiate(p, overrides);
    }
}
