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

package nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.factory.io.visual.highlight.BullseyeGlyphFactory;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.highlight.*;

import java.awt.*;

/**
 * Created by dbborens on 8/4/2015.
 */
public class BullseyeGlyphLoader extends GlyphLoader<BullseyeGlyph> {
    private final BullseyeGlyphFactory factory;
    private final BullseyeGlyphInterpolator interpolator;

    public BullseyeGlyphLoader() {
        factory = new BullseyeGlyphFactory();
        interpolator = new BullseyeGlyphInterpolator();
    }

    public BullseyeGlyphLoader(BullseyeGlyphFactory factory,
                               BullseyeGlyphInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public Glyph instantiate(MapObjectNode node, GeneralParameters p) {
        Color primary = interpolator.primary(node, p);
        factory.setPrimary(primary);

        Color secondary = interpolator.secondary(node, p);
        factory.setSecondary(secondary);

        Double size = interpolator.size(node, p.getRandom());
        factory.setSize(size);

        return factory.build();
    }
}
