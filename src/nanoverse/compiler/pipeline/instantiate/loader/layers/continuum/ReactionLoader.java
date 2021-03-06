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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.layers.continuum.ReactionFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.layers.continuum.Reaction;

import java.util.Random;

/**
 * Created by dbborens on 8/13/15.
 */
public class ReactionLoader extends Loader<Reaction> {

    private final ReactionFactory factory;
    private final ReactionInterpolator interpolator;

    public ReactionLoader() {
        factory = new ReactionFactory();
        interpolator = new ReactionInterpolator();
    }

    public ReactionLoader(ReactionFactory factory,
                          ReactionInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public Reaction instantiate(MapObjectNode node, Random random) {
        double exp = interpolator.exp(node, random);
        factory.setExp(exp);

        double inj = interpolator.inj(node, random);
        factory.setInj(inj);

        String layer = interpolator.layer(node);
        factory.setId(layer);

        return factory.build();
    }
}
