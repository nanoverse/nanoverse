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

package nanoverse.runtime.io.visual.highlight;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;

/**
 * Created by dbborens on 4/3/14.
 */
public class DotGlyph extends Glyph {

    private int radius;
    private Color color;
    private double size;

    /**
     * @param color The color of the dot.
     * @param size  The relative size of the dot, specified as a multiple of the edge size.
     */
    @FactoryTarget
    public DotGlyph(Color color, double size) {
        this.color = color;
        this.size = size;
    }

    @Override
    protected void internalInit() {
        radius = calcProportionalSize(size);
    }

    @Override
    public void overlay(Coordinate target, int frame, double time) {
        // First, get the center of the cell (in pixels).
        Coordinate center = translator.resolve(target, frame, time);

        int x = center.x() - radius;
        int y = center.y() - radius;

        graphics.setColor(color);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DotGlyph)) {
            return false;
        }

        DotGlyph other = (DotGlyph) obj;
        if (radius != other.radius) {
            return false;
        }

        if (!color.equals(other.color)) {
            return false;
        }

        return true;
    }
}
