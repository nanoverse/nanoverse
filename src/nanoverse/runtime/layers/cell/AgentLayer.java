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

package nanoverse.runtime.layers.cell;

import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.geometry.boundaries.HaltBoundary;
import nanoverse.runtime.layers.Layer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * @author David Bruce Borenstein
 * @test AgentIntegrationTest
 */
public class AgentLayer extends Layer {

    protected AgentLayerContent content;

    @FactoryTarget(displayName = "AgentLayer")
    public AgentLayer(Geometry geom) {
        geometry = geom;
        reset();
    }

    public AgentLookupManager getLookupManager() {
        return new AgentLookupManager(geometry, content);
    }

    public AgentUpdateManager getUpdateManager() {
        return new AgentUpdateManager(content);
    }

    public AgentLayerViewer getViewer() {
        return new AgentLayerViewer(content);
    }

    @Override
    public int hashCode() {
        return content != null ? content.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentLayer cellLayer = (AgentLayer) o;

        if (content != null ? !content.equals(cellLayer.content) : cellLayer.content != null)
            return false;

        return true;
    }

    @Override
    public AgentLayer clone() {
        AgentLayerContent contentClone = content.clone();
        AgentLayer clone = new AgentLayer(geometry);
        clone.content = contentClone;
        return clone;
    }

    @Override
    public String getId() {
        return "0";
    }

    @Override
    public void reset() {
        AgentLayerIndices indices = new AgentLayerIndices();

        // Oh man, do I hate the following two lines and the
        // "getComponentClasses" cloodge that makes them possible
        Class boundaryClass = geometry.getComponentClasses()[2];
        if (HaltBoundary.class.isAssignableFrom(boundaryClass)) {
            content = new HaltAgentLayerContent(geometry, indices);
        } else if (geometry.isInfinite()) {
            content = new InfiniteAgentLayerContent(geometry, indices);
        } else {
            content = new FiniteAgentLayerContent(geometry, indices);
        }
    }
}
