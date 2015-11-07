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

package nanoverse.compiler.pipeline.instantiate.loader.processes.continuum;

import nanoverse.compiler.pipeline.instantiate.factory.processes.continuum.ScheduleHoldFactory;
import nanoverse.compiler.pipeline.instantiate.loader.processes.ProcessLoader;
import nanoverse.compiler.pipeline.translate.nodes.MapObjectNode;
import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.processes.BaseProcessArguments;
import nanoverse.runtime.processes.continuum.ScheduleHold;

/**
 * Created by dbborens on 8/3/2015.
 */
public class ScheduleHoldLoader extends ProcessLoader<ScheduleHold> {
    private final ScheduleHoldFactory factory;
    private final ScheduleHoldInterpolator interpolator;

    public ScheduleHoldLoader() {
        factory = new ScheduleHoldFactory();
        interpolator = new ScheduleHoldInterpolator();
    }

    public ScheduleHoldLoader(ScheduleHoldFactory factory,
                              ScheduleHoldInterpolator interpolator) {
        this.factory = factory;
        this.interpolator = interpolator;
    }

    @Override
    public ScheduleHold instantiate(MapObjectNode node, LayerManager lm, GeneralParameters p) {
        BaseProcessArguments arguments = interpolator.arguments(node, lm, p);
        factory.setArguments(arguments);

        String layer = interpolator.layer(node);
        ContinuumLayerScheduler scheduler = interpolator.scheduler(lm, layer);
        factory.setScheduler(scheduler);

        return factory.build();
    }
}
