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

import compiler.pipeline.instantiate.helpers.LoadHelper;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import io.visual.highlight.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Random;

/**
 * Created by dbborens on 8/23/2015.
 */
public class HighlightInterpolator {

    private final LoadHelper load;

    public HighlightInterpolator() {
        load = new LoadHelper();
    }

    public HighlightInterpolator(LoadHelper load) {
        this.load = load;
    }


    public Integer channel(MapObjectNode node, Random random) {
        return load.anInteger(node, "channel", random);
    }

    public Glyph glyph(MapObjectNode node, GeneralParameters p) {
        GlyphLoader loader = (GlyphLoader) load.getLoader(node, "glyph", true);
        MapObjectNode cNode = (MapObjectNode) node.getMember("glyph");

        return loader.instantiate(cNode, p);
    }
}
