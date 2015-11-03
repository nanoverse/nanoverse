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
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.set.CustomSet;

import java.util.Set;
import java.util.stream.Stream;

public class CustomCoordinateSetFactory implements Factory<CustomSet> {

    private final CustomCoordinateSetFactoryHelper helper;
    private Stream<Coordinate> coordinates;

    public CustomCoordinateSetFactory() {
        helper = new CustomCoordinateSetFactoryHelper();
    }

    public CustomCoordinateSetFactory(CustomCoordinateSetFactoryHelper helper) {
        this.helper = helper;
    }

    public void setCoordinates(Stream<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public CustomSet build() {
        return helper.build(coordinates);
    }
}