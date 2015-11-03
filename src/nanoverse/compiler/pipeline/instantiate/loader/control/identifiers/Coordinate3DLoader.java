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

import nanoverse.compiler.pipeline.instantiate.factory.control.identifiers.Coordinate3DFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.identifiers.Coordinate3D;
import nanoverse.runtime.layers.LayerManager;

/**
 * Created by dbborens on 8/12/15.
 */
public class Coordinate3DLoader extends CoordinateSubclassLoader<Coordinate3D> {
    private final Coordinate3DFactory factory;
    private final Coordinate3DInterpolator interpolator;

    public Coordinate3DLoader() {
        factory = new Coordinate3DFactory();
        interpolator = new Coordinate3DInterpolator();
    }

    public Coordinate3DLoader(Coordinate3DFactory factory,
                              Coordinate3DInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Coordinate3D instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        int x = interpolator.x(node, p.getRandom());
        factory.setX(x);

        int y = interpolator.y(node, p.getRandom());
        factory.setY(y);

        int z = interpolator.z(node, p.getRandom());
        factory.setZ(z);

        int flags = interpolator.flags(node, p.getRandom());
        factory.setFlags(flags);

        return factory.build();
    }
}
