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
package nanoverse.compiler.pipeline.instantiate.factory.geometry.set;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.set.DiscSet;

public class DiscCoordinateSetFactory implements Factory<DiscSet> {

    private final DiscCoordinateSetFactoryHelper helper;

    private Geometry geom;
    private IntegerArgument radiusArg;
    private Coordinate offset;

    public DiscCoordinateSetFactory() {
        helper = new DiscCoordinateSetFactoryHelper();
    }

    public DiscCoordinateSetFactory(DiscCoordinateSetFactoryHelper helper) {
        this.helper = helper;
    }

    public void setGeom(Geometry geom) {
        this.geom = geom;
    }

    public void setRadiusArg(IntegerArgument radiusArg) {
        this.radiusArg = radiusArg;
    }

    public void setOffset(Coordinate offset) {
        this.offset = offset;
    }

    @Override
    public DiscSet build() {
        return helper.build(geom, radiusArg, offset);
    }
}