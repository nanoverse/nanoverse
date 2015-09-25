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
package nanoverse.compiler.pipeline.instantiate.factory.layers.continuum;

import nanoverse.runtime.layers.continuum.ContinuumLayerScheduler;
import nanoverse.runtime.layers.continuum.ScheduledOperations;
import nanoverse.runtime.layers.continuum.HoldManager;
import nanoverse.compiler.pipeline.instantiate.factory.Factory;

public class ContinuumLayerSchedulerFactory implements Factory<ContinuumLayerScheduler> {

    private final ContinuumLayerSchedulerFactoryHelper helper;

    private ScheduledOperations scheduledOperations;
    private HoldManager holdManager;

    public ContinuumLayerSchedulerFactory() {
        helper = new ContinuumLayerSchedulerFactoryHelper();
    }

    public ContinuumLayerSchedulerFactory(ContinuumLayerSchedulerFactoryHelper helper) {
        this.helper = helper;
    }

    public void setScheduledOperations(ScheduledOperations scheduledOperations) {
        this.scheduledOperations = scheduledOperations;
    }

    public void setHoldManager(HoldManager holdManager) {
        this.holdManager = holdManager;
    }

    @Override
    public ContinuumLayerScheduler build() {
        return helper.build(scheduledOperations, holdManager);
    }
}