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

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.io.visual.highlight.*;

public class HighlightFactory implements Factory<Highlight> {

    private final HighlightFactoryHelper helper;

    private Integer channel;
    private Glyph glyph;

    public HighlightFactory() {
        helper = new HighlightFactoryHelper();
    }

    public HighlightFactory(HighlightFactoryHelper helper) {
        this.helper = helper;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public void setGlyph(Glyph glyph) {
        this.glyph = glyph;
    }

    @Override
    public Highlight build() {
        return helper.build(channel, glyph);
    }
}