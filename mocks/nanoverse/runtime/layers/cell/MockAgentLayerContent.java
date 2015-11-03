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

import nanoverse.runtime.agent.Agent;
import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;

import java.util.List;
import java.util.stream.*;

public class MockAgentLayerContent extends AgentLayerContent {

    private List<Coordinate> imaginarySites;
    private String[] stateVector;
    private double[] healthVector;

    public MockAgentLayerContent(Geometry geom, AgentLayerIndices indices) {
        super(geom, indices);
    }

    public Agent get(Coordinate coord) {
        // Mock getter doesn't do any validation
        return map.get(coord);
    }

	/* stateVector */

    @Override
    public void sanityCheck(Coordinate coord) {

    }

    @Override
    public Stream<Coordinate> getImaginarySites() {
        return imaginarySites.stream();
    }

	/* healthVector */

    private void setImaginarySites(Stream<Coordinate> imaginarySites) {
        this.imaginarySites = imaginarySites.collect(Collectors.toList());
    }

    @Override
    public AgentLayerContent clone() {
        MockAgentLayerContent clone = new MockAgentLayerContent(geom, indices);
        clone.imaginarySites = imaginarySites.stream().collect(Collectors.toList());
        clone.stateVector = stateVector.clone();
        clone.healthVector = healthVector.clone();
        return clone;
    }

    public void setStateVector(String[] stateVector) {
        this.stateVector = stateVector;
    }

    public void setHealthVector(double[] healthVector) {
        this.healthVector = healthVector;
    }
}
