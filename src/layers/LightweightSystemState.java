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

import cells.BehaviorCell;
import cells.Cell;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import geometry.Geometry;
import layers.cell.CellLayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by dbborens on 3/26/14.
 */
public class LightweightSystemState extends SystemState {

    private double time;
    private int frame;
    private Map<Integer, Set<Coordinate>> highlights;

    private LayerManager layerManager;
    private Geometry geometry;

    public LightweightSystemState(Geometry geometry) {
        layerManager = new LayerManager();
        highlights = new HashMap<>();
        this.geometry = geometry;
    }


    public void setHighlights(Integer channelId, Set<Coordinate> sites) {
        highlights.put(channelId, sites);
    }

    @Override
    public LayerManager getLayerManager() {
        return layerManager;
    }

    @Override
    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public int getFrame() {
        return frame;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    @Override
    public boolean isHighlighted(int channel, Coordinate coord) {
        Set<Coordinate> highlightedSites = highlights.get(channel);
        return highlightedSites.contains(coord);
    }

    public void initCellLayer(int[] stateVector, double[] healthVector) {
        if (stateVector.length != geometry.getCanonicalSites().length) {
            throw new IllegalStateException("Actual number of data points not equal to expected number");
        }
        if (healthVector.length != geometry.getCanonicalSites().length) {
            throw new IllegalStateException("Actual number of data points not equal to expected number");
        }
        // Build cell layer.
        CellLayer cellLayer = new CellLayer(geometry);
        layerManager.setCellLayer(cellLayer);

        // Iterate over state vector.
        for (int i = 0; i < stateVector.length; i++) {

            // Convert index to coordinate.
            Coordinate coord = geometry.getCanonicalSites()[i];

            double health = healthVector[i];

            // If site is vacant, don't place anything
            int state = stateVector[i];
            if (state == 0) {
                continue;
            }

            loadCell(cellLayer, coord, health, state);
        }

    }

    private void loadCell(CellLayer cellLayer, Coordinate coord, double health, int state) {
        try {
            // Build a dummy cell with the correct state and health.
            Cell cell = new BehaviorCell(layerManager, state, health, 0.0, null);

            // Place it in the cell layer.
            cellLayer.getUpdateManager().place(cell, coord);
        } catch (HaltCondition hc) {
            StringBuilder message = new StringBuilder();
            message.append("Consistency failure: simulation halt event thrown while reconstructing state.\n");
            message.append("Halt condition: ");
            message.append(hc.toString());
            throw new IllegalStateException(message.toString(), hc);
        }
    }

//    public void initSoluteLayer(String id, double[] soluteVector) {
//        if (soluteVector.length != geometry.getCanonicalSites().length) {
//            throw new IllegalStateException("Actual number of data points not equal to expected number");
//        }
//        LightweightSoluteLayer soluteLayer = new LightweightSoluteLayer(geometry, layerManager, id);
//        for (int i = 0; i < soluteVector.length; i++) {
//            Coordinate coord = geometry.getCanonicalSites()[i];
//            double value = soluteVector[i];
//            soluteLayer.set(coord, value);
//        }
//
//        layerManager.addSoluteLayer(id, soluteLayer);
//    }
}
