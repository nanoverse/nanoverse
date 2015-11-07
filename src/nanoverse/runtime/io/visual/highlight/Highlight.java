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

import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * A lightweight tuple capturing the visualization rules for a single
 * highlight channel.
 * Created by dbborens on 7/28/2015.
 */
public class Highlight {
    private final Glyph glyph;
    private final Integer channel;

    @FactoryTarget
    public Highlight(Integer channel, Glyph glyph) {
        this.channel = channel;
        this.glyph = glyph;
    }

    public Glyph getGlyph() {
        return glyph;
    }

    public Integer getChannel() {
        return channel;
    }
}
