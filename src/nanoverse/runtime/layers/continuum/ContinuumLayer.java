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

package nanoverse.runtime.layers.continuum;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.Layer;
import nanoverse.runtime.structural.annotations.FactoryTarget;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by dbborens on 12/11/14.
 */
public class ContinuumLayer extends Layer {

    private ContinuumLayerScheduler scheduler;
    private ContinuumLayerContent content;

    @FactoryTarget
    public ContinuumLayer(ContinuumLayerScheduler scheduler, ContinuumLayerContent content, Geometry geometry) {
        this.scheduler = scheduler;
        this.content = content;
        this.geometry = geometry;
    }

    @Override
    public String getId() {
        return scheduler.getId();
    }

    @Override
    public void reset() {
        scheduler.reset();
        content.reset();
    }

    public ContinuumLayerScheduler getScheduler() {
        return scheduler;
    }

    public ContinuumAgentLinker getLinker() {
        Function<Coordinate, Double> stateLookup = c -> content.get(c);
        return scheduler.getLinker(stateLookup);
    }

    public Stream<Double> getStateStream() {
        return content.getStateStream();
    }

    public double getValueAt(Coordinate c) {
        return content.get(c);
    }
}
