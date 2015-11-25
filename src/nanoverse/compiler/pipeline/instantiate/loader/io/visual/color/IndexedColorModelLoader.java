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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.color;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.color.IndexedColorModelFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.color.*;
import nanoverse.runtime.io.visual.color.palettes.Palette;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.NotYetImplementedException;

/**
 * Created by dbborens on 8/10/2015.
 */
public class IndexedColorModelLoader extends ColorModelLoader<IndexedColorModel> {
    private final IndexedColorModelFactory factory;
    private final IndexedColorModelInterpolator interpolator;

    public IndexedColorModelLoader() {
        factory = new IndexedColorModelFactory();
        interpolator = new IndexedColorModelInterpolator();
    }

    public IndexedColorModelLoader(IndexedColorModelFactory factory, IndexedColorModelInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    public ColorManager instantiate(LayerManager lm, GeneralParameters p) {
        return instantiate(null, lm, p);
    }

    @Override
    public ColorManager instantiate(MapObjectNode cNode, LayerManager lm, GeneralParameters p) {
        Palette palette = interpolator.palette(cNode, lm, p);
        factory.setPalette(palette);

        return factory.build();
    }
}
