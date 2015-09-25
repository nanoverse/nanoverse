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

package nanoverse.compiler.pipeline.instantiate.loader.layers.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.layers.continuum.ContinuumLayerSchedulerFactory;
import nanoverse.compiler.pipeline.instantiate.loader.Loader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.geometry.Geometry;
import nanoverse.runtime.layers.continuum.*;

/**
 * Created by dbborens on 8/20/2015.
 */
public class ContinuumLayerSchedulerLoader extends Loader<ContinuumLayerScheduler> {

    private final ContinuumLayerSchedulerFactory factory;
    private final ContinuumLayerSchedulerInterpolator interpolator;

    public ContinuumLayerSchedulerLoader() {
        interpolator = new ContinuumLayerSchedulerInterpolator();
        factory = new ContinuumLayerSchedulerFactory();
    }

    public ContinuumLayerSchedulerLoader(ContinuumLayerSchedulerInterpolator interpolator,
                                         ContinuumLayerSchedulerFactory factory) {

        this.interpolator = interpolator;
        this.factory = factory;
    }

    public ContinuumLayerScheduler instantiate(MapObjectNode node,
                                               ContinuumLayerContent content,
                                               Geometry geometry,
                                               GeneralParameters p) {

        Boolean operators = interpolator.operators(node, p.getRandom());
        ScheduledOperations so = new ScheduledOperations(geometry, operators);
        factory.setScheduledOperations(so);

        HoldManagerLoader hmLoader = new HoldManagerLoader();
        HoldManager holdManager = hmLoader.instantiate(node, content, geometry, so);
        factory.setHoldManager(holdManager);

        return factory.build();
    }
}
