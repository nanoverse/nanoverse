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

package io.visual.map;

import control.identifiers.Coordinate;
import control.identifiers.Coordinate2D;
import io.visual.VisualizationProperties;

import java.awt.*;

/**
 * Creates a 2D rectangular map from the middle-Z slice of a 3D cuboid arena.
 * <p>
 * Created by David B Borenstein on 5/8/14.
 */
public class CubePixelTranslator extends PixelTranslator {
    private int zMiddle;

    @Override
    protected void calcLimits(VisualizationProperties mapState) {
        int xMin, xMax, yMin, yMax, zMin, zMax;

        xMin = 2147483647;
        xMax = -2147483648;

        yMin = 2147483647;
        yMax = -2147483648;

        zMin = 2147483647;
        zMax = -2147483648;

        // First, get middle Z coordinate.
        for (Coordinate c : mapState.getCoordinates()) {
            int z = c.z();

            if (z < zMin) {
                zMin = z;
            }

            if (z > zMax) {
                zMax = z;
            }
        }

        zMiddle = (zMax - zMin) / 2;

        // Next, find x and y extrema from the middle-Z plane.
        for (Coordinate c : mapState.getCoordinates()) {
            int x = c.x();
            int y = c.y();
            int z = c.z();

            if (z != zMiddle) {
                continue;
            }

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

        int dy = edge * (yMax - yMin + 1);
        int dx = edge * (xMax - xMin + 1);
        imageDims = new Coordinate2D(dx, dy, 0);
    }

    @Override
    protected void calcOrigin() {
        int x = edge / 2;
        int y = edge / 2;
        origin = new Coordinate2D(x, y, 0);
    }

    protected Coordinate indexToPixels(Coordinate c) {
        int x = c.x();
        int y = c.y();
        int z = c.z();

        if (z != zMiddle) {
            throw new IllegalStateException("Attempting to plot non-middle point in CubePixelTranslator.");
        }

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
        return (obj instanceof CubePixelTranslator);
    }

    @Override
    public Coordinate resolve(Coordinate c, int frame, double time) {
        return indexToPixels(c);
    }

    @Override
    public Polygon makePolygon(Coordinate c, int frame, double time) {
        Coordinate centerPx = resolve(c, frame, time);
        Polygon p = new Polygon();
        int d = edge / 2;

        p.addPoint(centerPx.x() - d, centerPx.y() - d);
        p.addPoint(centerPx.x() + d, centerPx.y() - d);
        p.addPoint(centerPx.x() + d, centerPx.y() + d);
        p.addPoint(centerPx.x() - d, centerPx.y() + d);

        return p;
    }

    @Override
    public double getDiagonal() {
        return Math.sqrt(2) * edge;
    }

    @Override
    public boolean isRetained(Coordinate c) {
        return (c.z() == zMiddle);
    }
}
