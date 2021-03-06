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
import nanoverse.runtime.io.visual.highlight.CrosshairsGlyph;

import java.awt.*;

public class CrosshairsGlyphFactory implements Factory<CrosshairsGlyph> {

    private final CrosshairsGlyphFactoryHelper helper;

    private Color color;
    private double circleSize;
    private double crossSize;

    public CrosshairsGlyphFactory() {
        helper = new CrosshairsGlyphFactoryHelper();
    }

    public CrosshairsGlyphFactory(CrosshairsGlyphFactoryHelper helper) {
        this.helper = helper;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setCircleSize(double circleSize) {
        this.circleSize = circleSize;
    }

    public void setCrossSize(double crossSize) {
        this.crossSize = crossSize;
    }

    @Override
    public CrosshairsGlyph build() {
        return helper.build(color, circleSize, crossSize);
    }
}