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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.color.palettes.Palette;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;

/**
 * Created by dbborens on 4/1/14.
 */
public class IndexedColorModel extends ColorManager {
    private final Palette<String> palette;

    @FactoryTarget
    public IndexedColorModel(Palette<String> palette) {
        this.palette = palette;
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        String name = systemState
            .getLayerManager()
            .getAgentLayer()
            .getViewer()
            .getName(c);

        return palette.apply(name);
    }


    @Override
    public Color getBorderColor() {
        return palette.getBorderColor();
    }
}
