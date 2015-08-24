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

package compiler.pipeline.instantiate.factory.agent.targets;

import agent.targets.*;
import compiler.pipeline.instantiate.factory.Factory;
import layers.LayerManager;
import processes.discrete.filter.Filter;

import java.util.Random;

/**
 * Created by dbborens on 8/24/2015.
 */
public abstract class TargetFactory<T extends TargetDescriptor> implements Factory<T> {

    private final TargetFactoryHelper<T> helper;

    private LayerManager layerManager;
    private Filter filter;
    private int maximum;
    private Random random;

    protected TargetFactory(TargetFactoryHelper helper) {
        this.helper = helper;
    }

    public void setLayerManager(LayerManager layerManager) {
        this.layerManager = layerManager;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    @Override
    public T build() {
        return helper.build(layerManager, filter, maximum, random);
    }

}
