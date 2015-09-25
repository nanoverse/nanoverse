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

package nanoverse.runtime.io.visual.kymograph;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.control.identifiers.Coordinate2D;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.map.PixelTranslator;

import java.awt.*;

/**
 * A 2D rectangular plot with X axis as time,
 * and Y axis as a 1-dimensional system state.
 * <p>
 * Created by dbborens on 5/20/14.
 */
public class KymoPixelTranslator extends PixelTranslator {

    @Override
    protected void calcLimits(VisualizationProperties properties) {
        int xMin, xMax, yMin, yMax;

        xMin = 2147483647;
        xMax = -2147483648;

        yMin = 2147483647;
        yMax = -2147483648;

        for (Coordinate c : properties.getCoordinates()) {
            int x = c.x();
            int y = c.y();

            if (x < xMin) {
                xMin = x;
            }

            if (x > xMax) {
                xMax = x;
            }

            if (y < yMin) {
                yMin = y;
            }

            if (y > yMax) {
                yMax = y;
            }
        }

        if ((xMin != 0) || (xMax != 0)) {
            throw new IllegalArgumentException("Received a non-zero x " +
                    "coordinate in kymograph. Kymograph requires a 1D system.");
        }

        int dy = edge * (yMax - yMin + 1);

        int n = properties.getFrames().length;
        int lastFrame = properties.getFrames()[n - 1];
        int dt = edge * (lastFrame + 1);
        imageDims = new Coordinate2D(dt, dy, 0);
    }

    @Override
    protected void calcOrigin() {
        int t = edge / 2;
        int y = edge / 2;
        origin = new Coordinate2D(t, y, 0);

    }

    @Override
    public Coordinate resolve(Coordinate c, int frame, double time) {
        int x = frame;
        int y = c.y();

        int xPixels = x * edge;
        int yPixels = y * edge;

        Coordinate center = new Coordinate2D(xPixels, yPixels, 0);

        int px = origin.x() + center.x();
        int py = imageDims.y() - origin.y() - center.y();

        Coordinate ret = new Coordinate2D(px, py, 0);
        return ret;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof KymoPixelTranslator);
    }

    @Override
    public Polygon makePolygon(Coordinate c, int frame, double time) {
        Coordinate centerPx = resolve(c, frame, time);
        Polygon p = new Polygon();
        int d = edge / 2;

        // This is broken right now, and using outlines will require a rethink.
        p.addPoint(centerPx.x() - d, centerPx.y() - d - 1);
        p.addPoint(centerPx.x() + d + 1, centerPx.y() - d - 1);
        p.addPoint(centerPx.x() + d + 1, centerPx.y() + d);
        p.addPoint(centerPx.x() - d, centerPx.y() + d);

        return p;
    }

    @Override
    public double getDiagonal() {
        return Math.sqrt(2) * edge;
    }

    @Override
    public boolean isRetained(Coordinate c) {
        return true;
    }
}
