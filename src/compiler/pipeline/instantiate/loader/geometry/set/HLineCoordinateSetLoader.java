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

package compiler.pipeline.instantiate.loader.geometry.set;

import compiler.pipeline.instantiate.factory.geometry.set.HLineCoordinateSetFactory;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import control.identifiers.Coordinate;
import geometry.Geometry;
import geometry.set.HorizontalLineSet;
import layers.LayerManager;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by dbborens on 8/4/2015.
 */
public class HLineCoordinateSetLoader extends CoordinateSetLoader<HorizontalLineSet> {

    private final HLineCoordinateSetFactory factory;
    private final HLineCoordinateSetInterpolator interpolator;

    public HLineCoordinateSetLoader() {
        factory = new HLineCoordinateSetFactory();
        interpolator = new HLineCoordinateSetInterpolator();
    }

    public HLineCoordinateSetLoader(HLineCoordinateSetFactory factory,
                                    HLineCoordinateSetInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public HorizontalLineSet instantiate(ObjectNode oNode, LayerManager lm, GeneralParameters p) {
        MapObjectNode node = (MapObjectNode) oNode;

        Geometry geometry = interpolator.geometry(lm);
        factory.setGeom(geometry);

        int length = interpolator.length(node, p.getRandom());
        factory.setLength(length);

        Coordinate start = interpolator.start(node, lm, p);
        factory.setStart(start);

        return factory.build();
    }
}
