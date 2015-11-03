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

package nanoverse.runtime.layers;

import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.structural.NotYetImplementedException;

/**
 * Created by dbborens on 4/2/14.
 */
@Deprecated
public class MockSystemState extends SystemState {

    private boolean highlighted;
    private LayerManager layerManager;

    public MockSystemState() {
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    @Override
    public Extrema getContinuumExtrema(String id) {
        throw new NotYetImplementedException();
    }

    @Override
    public double getContinuumValue(String id, Coordinate c) {
        throw new NotYetImplementedException();
    }

    @Override
    public double getTime() {
        return 0;
    }

    @Override
    public int getFrame() {
        return 0;
    }

    @Override
    public boolean isHighlighted(int channel, Coordinate coord) {
        return highlighted;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setHighlighted(boolean value) {
        highlighted = value;
    }

}
