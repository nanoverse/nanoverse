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

package nanoverse.runtime.io.visual.color;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.SystemState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.awt.*;
import java.util.stream.Stream;

/**
 * Color manager that shows surface agents in a vibrant color, and interior
 * agents in a dull color. This operates as an overlay on an existing color
 * scheme.
 * <p>
 * Created by dbborens on 7/23/14.
 */
public class SurfaceColorModel extends ColorManager {

    private final ColorManager base;
    private final DiscreteColorAdjuster adjuster;
    private final InteriorChecker interiorChecker;

    @FactoryTarget(displayName = "SurfaceColorModel")
    public SurfaceColorModel(ColorManager base,
                             float luminanceScale,
                             float saturationScale) {
        this.base = base;
        adjuster = new DiscreteColorAdjuster(saturationScale, luminanceScale);
        interiorChecker = new InteriorChecker();
    }

    /**
     * Testing constructor
     */
    public SurfaceColorModel(ColorManager base, DiscreteColorAdjuster adjuster, InteriorChecker interiorChecker) {
        this.base = base;
        this.adjuster = adjuster;
        this.interiorChecker = interiorChecker;
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        Color baseColor = base.getColor(c, systemState);

        if (isVacant(systemState, c)) {
            return Color.BLACK;
        }

        // Scale lightness appropriately
        if (interiorChecker.isInterior(c, systemState)) {
            return adjuster.adjustColor(baseColor);
        } else {
            return baseColor;
        }
    }

    private boolean isVacant(SystemState systemState, Coordinate c) {
        return !systemState
                .getLayerManager()
                .getAgentLayer()
                .getViewer()
                .isOccupied(c);
    }



    @Override
    public Color getBorderColor() {
        return Color.DARK_GRAY;
    }
}
