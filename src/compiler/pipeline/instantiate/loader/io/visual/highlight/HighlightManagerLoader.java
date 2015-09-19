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
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.*;
import control.GeneralParameters;
import io.visual.highlight.*;

import java.util.Map;

/**
 * Created by dbborens on 8/13/15.
 */
public class HighlightManagerLoader extends Loader<HighlightManager> {

    private final HighlightManagerFactory factory;
    private final HighlightManagerChildLoader cLoader;

    public HighlightManagerLoader() {
        factory = new HighlightManagerFactory();
        cLoader = new HighlightManagerChildLoader();
    }

    public HighlightManagerLoader(HighlightManagerFactory factory,
                                  HighlightManagerChildLoader cLoader) {
        this.factory = factory;
        this.cLoader = cLoader;
    }

    public HighlightManager instantiate(ListObjectNode cNode,
                                        GeneralParameters p) {

        cNode.getMemberStream()
            .forEach(node -> cLoader.load(node, p));
        Map<Integer, Glyph> glyphMap = cLoader.getGlyphMap();
        factory.setGlyphMap(glyphMap);

        return factory.build();
    }

    public HighlightManager instantiate(GeneralParameters p) {
        return instantiate(null, p);
    }
}
