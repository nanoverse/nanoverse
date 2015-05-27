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

package layers.continuum;

import control.identifiers.Coordinate;
import geometry.Geometry;
import layers.Layer;
import no.uib.cipr.matrix.Vector;

import java.util.function.Function;
import java.util.stream.*;

/**
 * Created by dbborens on 12/11/14.
 */
public class ContinuumLayer extends Layer {

    private ContinuumLayerScheduler scheduler;
    private ContinuumLayerContent content;

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
}
