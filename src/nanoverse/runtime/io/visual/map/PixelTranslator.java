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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.VisualizationProperties;

import java.awt.*;

/**
 * Manages the translation from coordinates to pixels in map visualizations.
 * <p>
 * Created by dbborens on 4/1/14.
 */
public abstract class PixelTranslator {


    // Dimensions of the image (in units of pixels)
    protected Coordinate imageDims;

    // Lower left coordinate, in pixel space
    protected Coordinate origin;

    protected int edge;

    /**
     * Load required state from the map and construct coordinate to pixel
     * translations.
     *
     * @param properties
     */
    public void init(VisualizationProperties properties) {
        edge = properties.getEdge();
        Coordinate[] coordinates = properties.getCoordinates();
        properties.setCoordinates(coordinates);
        calcLimits(properties);
        calcOrigin();
    }

    protected abstract void calcLimits(VisualizationProperties mapState);

    // Provide the coordinate of the lower-leftmost coordinate to be included in.
    // the field of view. This may not be a coordinate that exists in this
    // nanoverse.runtime.geometry (ie, in the case of non-rectangular geometries).
    protected abstract void calcOrigin();

    /**
     * Convert coordinate (in the cell-based coordinate system of the model)
     * to the pixel coordinate of the center of the coordinate.
     *
     * @return
     */
    public abstract Coordinate resolve(Coordinate c, int frame, double time);

    public Coordinate getImageDims() {
        return imageDims;
    }

    @Override
    public abstract boolean equals(Object obj);

    public abstract Polygon makePolygon(Coordinate c, int frame, double time);

    /**
     * Return the length of the diagonal of a polygon, based on the nanoverse.runtime.geometry
     * of the lattice and the length of the edges.
     */
    public abstract double getDiagonal();

    /**
     * Returns true iff the specified coordinate is to be plotted.
     *
     * @param c
     * @return
     */
    public abstract boolean isRetained(Coordinate c);
}
