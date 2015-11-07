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

package nanoverse.compiler.pipeline.instantiate.loader.control.identifiers;

import nanoverse.compiler.pipeline.instantiate.factory.control.identifiers.Coordinate2DFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/12/15.
 */
public class Coordinate2DLoader extends CoordinateSubclassLoader<Coordinate2D> {
    private final Coordinate2DFactory factory;
    private final Coordinate2DInterpolator interpolator;

    public Coordinate2DLoader() {
        factory = new Coordinate2DFactory();
        interpolator = new Coordinate2DInterpolator();
    }

    public Coordinate2DLoader(Coordinate2DFactory factory,
                              Coordinate2DInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Coordinate2D instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        int x = interpolator.x(node, p.getRandom());
        factory.setX(x);

        int y = interpolator.y(node, p.getRandom());
        factory.setY(y);

        int flags = interpolator.flags(node, p.getRandom());
        factory.setFlags(flags);

        return factory.build();
    }
}
