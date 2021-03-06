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

package nanoverse.runtime.io.visual.highlight;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.map.PixelTranslator;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.util.*;

/**
 * Created by dbborens on 4/2/14.
 */
public class HighlightManager {
    private final Map<Integer, Glyph> glyphMap;

    @FactoryTarget
    public HighlightManager(Map<Integer, Glyph> glyphMap) {
        this.glyphMap = glyphMap;
    }

    public HighlightManager() {
        glyphMap = new HashMap<>();
    }

    public void setGlyph(int channel, Glyph glyph) {
        glyphMap.put(channel, glyph);
    }

    public void setGraphics(Graphics2D graphics) {
        for (Glyph glyph : glyphMap.values()) {
            glyph.setGraphics(graphics);
        }
    }

    public void render(Coordinate c, SystemState systemState) {
        for (int channel : glyphMap.keySet()) {
            if (systemState.isHighlighted(channel, c)) {
                Glyph glyph = glyphMap.get(channel);
                glyph.overlay(c, systemState.getFrame(), systemState.getTime());
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof HighlightManager)) {
            return false;
        }

        HighlightManager other = (HighlightManager) obj;

        if (other.glyphMap.size() != glyphMap.size()) {
            return false;
        }

        for (Integer key : glyphMap.keySet()) {
            if (!other.glyphMap.containsKey(key)) {
                return false;
            }

            Glyph p = glyphMap.get(key);
            Glyph q = other.glyphMap.get(key);
            if (!p.equals(q)) {
                return false;
            }
        }

        return true;
    }

    public void init(PixelTranslator translator) {
        for (Glyph glyph : glyphMap.values()) {
            glyph.init(translator);
        }
    }

    public int[] getHighlightChannels() {
        int[] channels = new int[glyphMap.size()];
        int i = 0;
        for (Integer channel : glyphMap.keySet()) {
            channels[i] = channel;
            i++;
        }

        return channels;
    }
}
