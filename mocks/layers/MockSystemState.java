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

package layers;

import control.identifiers.Coordinate;

/**
 * Created by dbborens on 4/2/14.
 */
public class MockSystemState extends SystemState {

    private boolean highlighted;
    private LayerManager layerManager;

    public MockSystemState() {
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    @Override
    public double getTime() {
        return 0;
    }

    @Override
    public int getFrame() {
        return 0;
    }

    public void setHighlighted(boolean value) {
        highlighted = value;
    }

    @Override
    public boolean isHighlighted(int channel, Coordinate coord) {
        return highlighted;
    }

}
