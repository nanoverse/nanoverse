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
 * Created by dbborens on 7/4/2015.
 */
public class UniformColorManager extends ColorManager {

    private final Color color;

    @FactoryTarget(displayName = "UniformColorModel")
    public UniformColorManager(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor(Coordinate c, SystemState systemState) {
        return color;
    }

    @Override
    public Color getBorderColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UniformColorManager that = (UniformColorManager) o;

        return !(color != null ? !color.equals(that.color) : that.color != null);

    }

    @Override
    public int hashCode() {
        return color != null ? color.hashCode() : 0;
    }
}
