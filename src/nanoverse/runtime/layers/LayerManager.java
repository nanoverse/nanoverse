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

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.layers.cell.AgentLayer;
import nanoverse.runtime.layers.continuum.ContinuumLayer;
import nanoverse.runtime.processes.StepState;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by David B Borenstein on 12/29/13.
 */
public class LayerManager {

    protected AgentLayer cellLayer;

    protected HashMap<String, ContinuumLayer> continuumLayers;
    private StepState stepState;

    @FactoryTarget
    public LayerManager(AgentLayer cellLayer,
                        HashMap<String, ContinuumLayer> continuumLayers) {

        this.cellLayer = cellLayer;
        this.continuumLayers = continuumLayers;
    }

    public LayerManager() {
        continuumLayers = new HashMap<>();
    }

    public AgentLayer getAgentLayer() {
        return cellLayer;
    }

    public void setAgentLayer(AgentLayer cellLayer) {
        this.cellLayer = cellLayer;
    }

    @Override
    public int hashCode() {
        int result = cellLayer != null ? cellLayer.hashCode() : 0;
        result = 31 * result + (continuumLayers != null ? continuumLayers.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LayerManager)) {
            return false;
        }

        LayerManager that = (LayerManager) o;

        if (cellLayer != null ? !cellLayer.equals(that.cellLayer) : that.cellLayer != null)
            return false;

        if (continuumLayers.size() != that.continuumLayers.size()) {
            return false;
        }

        for (String s : continuumLayers.keySet()) {
            if (!that.continuumLayers.containsKey(s)) {
                return false;
            }

            ContinuumLayer p = continuumLayers.get(s);
            ContinuumLayer q = that.continuumLayers.get(s);
            if (!p.equals(q)) {
                return false;
            }
        }

        return true;
    }

    public StepState getStepState() {
        return stepState;
    }

    public void setStepState(StepState stepState) {
        this.stepState = stepState;
    }

    public void reset() {
        if (cellLayer != null) {
            cellLayer.reset();
        }
        continuumLayers.values()
            .stream()
            .forEach(ContinuumLayer::reset);
    }

    /**
     * Returns a linker through which agents can retrieve the state of a
     * continuum at their location, or notify the field of their birth or
     * death.
     *
     * @param id
     * @return
     */
    public ContinuumLayer getContinuumLayer(String id) {
        ContinuumLayer layer = continuumLayers.get(id);
        return layer;
    }

    public Stream<String> getContinuumLayerIds() {
        return continuumLayers.keySet().stream();
    }

    // TODO This assumes that all nanoverse.runtime.layers have same coordinate index (which they should--but this is not enforced)
    public Function<Integer, Coordinate> getDeindexer() {
        return i -> cellLayer.getGeometry().getCanonicalSites()[i];
    }

    @Deprecated
    public void addContinuumLayer(ContinuumLayer continuumLayer) {
        String id = continuumLayer.getId();
        continuumLayers.put(id, continuumLayer);
    }
}
