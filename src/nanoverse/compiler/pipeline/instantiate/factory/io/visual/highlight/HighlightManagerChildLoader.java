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

package nanoverse.compiler.pipeline.instantiate.factory.io.visual.highlight;

import nanoverse.compiler.error.IllegalAssignmentError;
import nanoverse.compiler.pipeline.instantiate.loader.io.visual.highlight.HighlightLoader;
import nanoverse.compiler.pipeline.translate.nodes.*;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.io.visual.highlight.*;

import java.util.*;

/**
 * Created by dbborens on 8/23/2015.
 */
public class HighlightManagerChildLoader {

    private final Map<Integer, Glyph> glyphMap;

    public HighlightManagerChildLoader() {
        glyphMap = new HashMap<>();
    }

    public HighlightManagerChildLoader(Map<Integer, Glyph> glyphMap) {
        this.glyphMap = glyphMap;
    }

    public void load(ObjectNode node, GeneralParameters p) {
        MapObjectNode mNode = (MapObjectNode) node;
        HighlightLoader loader = (HighlightLoader) mNode.getSymbolTable()
            .getLoader();

        Highlight highlight = loader.instantiate(mNode, p);
        Integer channel = highlight.getChannel();
        Glyph glyph = highlight.getGlyph();

        if (glyphMap.containsKey(channel)) {
            throw new IllegalAssignmentError("Double assignment to highlight " +
                "channel " + channel + " in highlight renderer specification", node.lineNumber());
        }

        glyphMap.put(channel, glyph);
    }

    public Map<Integer, Glyph> getGlyphMap() {
        return glyphMap;
    }
}
