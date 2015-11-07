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

package nanoverse.compiler.pipeline.instantiate.loader.geometry.set;

import nanoverse.compiler.pipeline.instantiate.factory.geometry.set.CustomCoordinateSetFactory;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CustomSet;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.processes.NanoverseProcess;

import java.util.stream.Stream;

/**
 * Created by dbborens on 8/4/2015.
 */
public class CustomCoordinateSetLoader extends CoordinateSetLoader<CustomSet> {
    private final CustomCoordinateSetFactory factory;
    private final CustomCoordinateSetInterpolator interpolator;

    public CustomCoordinateSetLoader() {
        factory = new CustomCoordinateSetFactory();
        interpolator = new CustomCoordinateSetInterpolator();
    }

    public CustomCoordinateSetLoader(CustomCoordinateSetFactory factory,
                                     CustomCoordinateSetInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public CustomSet instantiate(ObjectNode o, LayerManager lm, GeneralParameters p) {
        ListObjectNode node = (ListObjectNode) o;
        Stream<Coordinate> coordinates = interpolator.coordinates(node, lm, p);
        factory.setCoordinates(coordinates);
        return factory.build();
    }
}
