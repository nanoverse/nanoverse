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

package io.visual;

import geometry.Geometry;
import layers.SystemState;

import java.awt.*;

/**
 * Superclass for defining visualizations based on system state data.
 * The Visualization returns an Image object upon render. Depending on
 * user needs, this visualization may then either be written to disk or
 * to the screen. A single visualization object may be used to generate
 * visualizations from several simulations.
 * <p>
 * Created by David B Borenstein on 3/23/14.
 */
public abstract class Visualization {

    protected VisualizationProperties properties;

    /**
     * Perform any actions necessary before frames may be written, but
     * which have to be called after the constructor.
     *
     * @param geometry The Geometry object from which the lattice
     *                 connectivity, size and shape will be drawn.
     *                 Boundary conditions should be ignored.
     */
    public abstract void init(Geometry geometry, double[] times, int[] frames);

    /**
     * Render a frame.
     *
     * @param systemState
     */
    public abstract Image render(SystemState systemState);

    /**
     * Perform any actions necessary for finalizing the current visualization.
     */
    public abstract void conclude();

    /**
     * Get a list of solute field IDs that are expected for this visualization.
     */
    public abstract String[] getSoluteIds();

    /**
     * Get a list of highlight channels that are expected for this visualization.
     */
    public abstract int[] getHighlightChannels();
}
