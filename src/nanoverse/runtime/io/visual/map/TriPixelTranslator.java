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

package nanoverse.runtime.io.visual.map;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.io.visual.VisualizationProperties;

import java.awt.*;

/**
 * Created by dbborens on 4/1/14.
 */
public class TriPixelTranslator extends PixelTranslator {

    private int yOffset;
    private int dy;
    private int dx;

    @Override
    public void init(VisualizationProperties properties) {
        int edge = properties.getEdge();
        dx = 3 * edge / 2;

        double dyFP = Math.sqrt(3.0f) * edge;
        dy = (int) Math.round(dyFP);

        // We want both dx and dy to be even
        if (dy % 2 != 0) {
            dy += 1;
        }

        super.init(properties);
    }

    @Override
    protected void calcLimits(VisualizationProperties mapState) {
        int xMin, xMax, yMin, yMax;

        xMin = 2147483647;
        xMax = -2147483648;

        yMin = 2147483647;
        yMax = -2147483648;

        for (Coordinate c : mapState.getCoordinates()) {
            Coordinate pixels = indexToOffset(c.x(), c.y());
            int x = pixels.x();
            int y = pixels.y();

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

        int y = (yMax - yMin) + dy;
        int x = xMax - xMin + (2 * edge) + 1;
        imageDims = new Coordinate2D(x, y, 0);
        calcYOffset(yMin);
    }

    private void calcYOffset(int y) {
        int y0 = indexToOffset(0, 0).y() - (dy / 2);
        yOffset = y - y0;
    }

    @Override
    protected void calcOrigin() {
        int x = (int) Math.floor(edge);
        int y = dy - yOffset;
        origin = new Coordinate2D(x, y, 0);
    }

    @Override
    public Coordinate resolve(Coordinate c, int frame, double time) {
        return indexToPixels(c);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof TriPixelTranslator);
    }

    public Polygon makePolygon(Coordinate coord, int frame, double time) {
        Coordinate centerPx = resolve(coord, frame, time);
        Polygon p = new Polygon();
        p.addPoint(centerPx.x() + edge, centerPx.y());
        p.addPoint(centerPx.x() + (edge / 2), centerPx.y() - (dy / 2));
        p.addPoint(centerPx.x() - (edge / 2) - 1, centerPx.y() - (dy / 2));
        p.addPoint(centerPx.x() - (edge) - 1, centerPx.y());
        p.addPoint(centerPx.x() - (edge / 2) - 1, centerPx.y() + (dy / 2));
        p.addPoint(centerPx.x() + (edge / 2), centerPx.y() + (dy / 2));

        return p;
    }

    @Override
    public double getDiagonal() {
        return 2.0 * edge;
    }

    @Override
    public boolean isRetained(Coordinate c) {
        return true;
    }

    protected Coordinate indexToPixels(Coordinate c) {
        int x = c.x();
        int y = c.y();

        Coordinate center = indexToOffset(x, y);

        int px = origin.x() + center.x();
        int py = imageDims.y() - origin.y() - center.y();

        Coordinate ret = new Coordinate2D(px, py, 0);
        return ret;
    }

    private Coordinate2D indexToOffset(int x, int y) {
        int ox = dx * x;
        int oy = -(dy * x / 2) + (dy * y);
        Coordinate2D ret = new Coordinate2D(ox, oy, 0);
        return (ret);
    }

}
