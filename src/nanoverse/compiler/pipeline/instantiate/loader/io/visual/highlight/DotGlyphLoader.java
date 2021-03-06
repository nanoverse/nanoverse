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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.highlight.DotGlyphFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.highlight.*;

import java.awt.*;

/**
 * Created by dbborens on 8/4/2015.
 */
public class DotGlyphLoader extends GlyphLoader<DotGlyph> {

    private final DotGlyphFactory factory;
    private final DotGlyphInterpolator interpolator;

    public DotGlyphLoader() {
        factory = new DotGlyphFactory();
        interpolator = new DotGlyphInterpolator();
    }

    public DotGlyphLoader(DotGlyphFactory factory,
                          DotGlyphInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Glyph instantiate(MapObjectNode node, GeneralParameters p) {
        Color color = interpolator.color(node, p);
        factory.setColor(color);

        double size = interpolator.size(node, p.getRandom());
        factory.setSize(size);

        return factory.build();
    }
}
