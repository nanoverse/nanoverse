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

package nanoverse.runtime.layers;

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.halt.HaltCondition;
import nanoverse.runtime.control.identifiers.*;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.io.deserialize.continuum.ContinuumLayerViewer;
import nanoverse.runtime.layers.cell.AgentLayer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by dbborens on 3/26/14.
 */
public class LightweightSystemState extends SystemState {

    private double time;
    private int frame;
    private Map<Integer, Set<Coordinate>> highlights;
    private Geometry geometry;

    // Continuum values
    private Map<String, Extrema> extremaMap;
    private ContinuumLayerViewer continuumViewer;

    // Legacy logic for handling nanoverse.runtime.agent locations
    private LayerManager layerManager;

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
    public Extrema getContinuumExtrema(String id) {
        return extremaMap.get(id);
    }

    @Override
    public double getContinuumValue(String id, Coordinate c) {
        int index = geometry.getIndexer().apply(c);
        return continuumViewer.getValue(id, index);
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

    public void setAgentNames(Stream<String> nameStream) {
        List<String> names = nameStream.collect(Collectors.toList());

        if (names.size() != geometry.getCanonicalSites().length) {
            throw new IllegalStateException("Actual number of data points not equal to expected number");
        }

        // Build agent layer.
        AgentLayer agentLayer = new AgentLayer(geometry);
        layerManager.setAgentLayer(agentLayer);

        IntStream.range(0, names.size())
                .forEach(i -> {
                    Coordinate coord = geometry.getCanonicalSites()[i];
                    String name = names.get(i);
                    loadAgent(agentLayer, coord, name);
                });
    }

    //    private void loadAgent(AgentLayer agentLayer, Coordinate coord, double health, int state) {
    private void loadAgent(AgentLayer agentLayer, Coordinate coord, String name) {
        try {
            // Build a dummy agent with the correct state and health.
            Agent agent = new Agent(layerManager, name, null);

            // Place it in the agent layer.
            agentLayer.getUpdateManager().place(agent, coord);
        } catch (HaltCondition hc) {
            StringBuilder message = new StringBuilder();
            message.append("Consistency failure: simulation halt event thrown while reconstructing state.\n");
            message.append("Halt condition: ");
            message.append(hc.toString());
            throw new IllegalStateException(message.toString(), hc);
        }
    }

    public void setContinuumLayerViewer(ContinuumLayerViewer continuumViewer) {
        this.continuumViewer = continuumViewer;
    }

    public void setExtremaMap(Map<String, Extrema> extremaMap) {
        this.extremaMap = extremaMap;
    }
}
