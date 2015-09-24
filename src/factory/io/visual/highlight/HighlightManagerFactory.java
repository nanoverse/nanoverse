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

package factory.io.visual.highlight;

import factory.io.visual.glyph.GlyphFactory;
import io.visual.highlight.Glyph;
import io.visual.highlight.HighlightManager;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * Created by dbborens on 4/3/14.
 */
public abstract class HighlightManagerFactory {
    public static HighlightManager instantiate(Element highlightRoot) {
        HighlightManager renderer = new HighlightManager();

        // No highlight tag? Fine. Return the empty manager.
        if (highlightRoot == null) {
            return renderer;
        }

        for (Object o : highlightRoot.elements()) {
            loadGlyph(renderer, (Element) o);
        }

        return renderer;
    }

    private static void loadGlyph(HighlightManager renderer, Element node) {
        Integer channel = getChannel(node);
        Element glyphNode = node.element("glyph");
        Glyph glyph = GlyphFactory.instantiate(glyphNode);
        renderer.setGlyph(channel, glyph);
    }

    private static Integer getChannel(Element node) {
        Attribute indexAttrib = node.attribute("index");
        String indexText = indexAttrib.getText();
        Integer channel = Integer.valueOf(indexText);
        return channel;
    }

}
