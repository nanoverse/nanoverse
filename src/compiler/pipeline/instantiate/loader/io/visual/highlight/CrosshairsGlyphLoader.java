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

package compiler.pipeline.instantiate.loader.io.visual.highlight;

import compiler.pipeline.instantiate.factory.io.visual.highlight.*;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import io.visual.highlight.*;

import java.awt.*;

/**
 * Created by dbborens on 8/4/2015.
 */
public class CrosshairsGlyphLoader extends GlyphLoader<CrosshairsGlyph> {

    private final CrosshairsGlyphFactory factory;
    private final CrosshairsGlyphInterpolator interpolator;

    public CrosshairsGlyphLoader() {
        factory = new CrosshairsGlyphFactory();
        interpolator = new CrosshairsGlyphInterpolator();
    }

    public CrosshairsGlyphLoader(CrosshairsGlyphFactory factory,
                               CrosshairsGlyphInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Glyph instantiate(MapObjectNode node, GeneralParameters p) {
        double crossSize = interpolator.crossSize(node, p.getRandom());
        factory.setCrossSize(crossSize);

        double circleSize = interpolator.circleSize(node, p.getRandom());
        factory.setCircleSize(circleSize);

        Color color = interpolator.color(node, p);
        factory.setColor(color);

        return factory.build();
    }
}
