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
import io.deserialize.CoordinateDeindexer;
import layers.cell.CellLayer;
import layers.continuum.ContinuumLayer;
import processes.StepState;
import structural.annotations.FactoryTarget;

import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by David B Borenstein on 12/29/13.
 */
public class LayerManager {

    protected CellLayer cellLayer;

    protected HashMap<String, ContinuumLayer> continuumLayers;
    private StepState stepState;

    @FactoryTarget
    public LayerManager(CellLayer cellLayer,
                        HashMap<String, ContinuumLayer> continuumLayers) {

        this.cellLayer = cellLayer;
        this.continuumLayers = continuumLayers;
    }

    public CellLayer getCellLayer() {
        return cellLayer;
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

    @Override
    public int hashCode() {
        int result = cellLayer != null ? cellLayer.hashCode() : 0;
        result = 31 * result + (continuumLayers != null ? continuumLayers.hashCode() : 0);
        return result;
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

    // TODO This assumes that all layers have same coordinate index (which they should--but this is not enforced)
    public Function<Integer, Coordinate> getDeindexer() {
        return i -> cellLayer.getGeometry().getCanonicalSites()[i];
    }

    // TODO: Remove deprecated methods after deploying compiler
    @Deprecated
    public LayerManager() {
        continuumLayers = new HashMap<>();
    }

    @Deprecated
    public void addContinuumLayer(ContinuumLayer continuumLayer) {
        String id = continuumLayer.getId();
        continuumLayers.put(id, continuumLayer);
    }

    @Deprecated
    public void setCellLayer(CellLayer cellLayer) {
        this.cellLayer = cellLayer;
    }
}
