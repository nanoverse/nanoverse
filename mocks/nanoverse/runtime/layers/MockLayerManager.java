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

import nanoverse.runtime.layers.cell.AgentLayer;

/**
 * Created by dbborens on 12/31/13.
 */
public class MockLayerManager extends LayerManager {
    public MockLayerManager() {
        super();
//        soluteLayers = new HashMap<>();
    }

    public AgentLayer getAgentLayer() {
        return cellLayer;
    }

    public void setAgentLayer(AgentLayer cellLayer) {
        this.cellLayer = cellLayer;
    }

//    public void addSoluteLayer(String s, SoluteLayer layer) {
//        soluteLayers.put(s, layer);
//    }

//    public SoluteLayer getSoluteLayer(String s) {
//        return soluteLayers.get(s);
//    }

//    public SoluteLayer[] getSoluteLayers() {
//        return soluteLayers.values().toArray(new SoluteLayer[0]);
//    }
}
