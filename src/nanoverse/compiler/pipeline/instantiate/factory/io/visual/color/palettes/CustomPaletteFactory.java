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

package nanoverse.compiler.pipeline.instantiate.factory.io.visual.color.palettes;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.io.visual.color.palettes.CustomPalette;

import java.awt.*;
import java.util.Map;

public class CustomPaletteFactory implements Factory<CustomPalette> {

    private final CustomPaletteFactoryHelper helper;

    private Color nullValueColor;
    private Color borderColor;
    private Color defaultColor;
    private Map mappings;

    public CustomPaletteFactory() {
        helper = new CustomPaletteFactoryHelper();
    }

    public CustomPaletteFactory(CustomPaletteFactoryHelper helper) {
        this.helper = helper;
    }

    public void setNullValueColor(Color nullValueColor) {
        this.nullValueColor = nullValueColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void setDefaultColor(Color defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setMappings(Map mappings) {
        this.mappings = mappings;
    }

    @Override
    public CustomPalette build() {
        return helper.build(nullValueColor, borderColor, defaultColor, mappings);
    }
}