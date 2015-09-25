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

/**
 * Created by dbborens on 4/1/14.
 */
public class DefaultColorManager extends ColorManager {

    @FactoryTarget(displayName = "IndexedColorModel")
    public DefaultColorManager() {
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        int state = systemState.getLayerManager().getCellLayer().getViewer().getState(c);

        switch (state) {
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.YELLOW;
            default:
                throw new UnsupportedOperationException("Default color manager supports only states 1 and 2, or dead.");
        }
    }

    @Override
    public Color getBorderColor() {
        return Color.DARK_GRAY;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof DefaultColorManager);
    }
}
