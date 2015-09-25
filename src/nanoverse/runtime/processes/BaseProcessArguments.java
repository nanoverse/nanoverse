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

package nanoverse.runtime.processes;

import nanoverse.runtime.control.GeneralParameters;
import nanoverse.runtime.control.arguments.IntegerArgument;
import nanoverse.runtime.layers.LayerManager;
import nanoverse.runtime.structural.annotations.FactoryTarget;

/**
 * Created by dbborens on 11/23/14.
 */
public class BaseProcessArguments {
    private GeneralParameters generalParameters;
    private int id;
    private IntegerArgument start;
    private IntegerArgument period;

    private LayerManager layerManager;

    @FactoryTarget
    public BaseProcessArguments(LayerManager layerManager, GeneralParameters generalParameters, int id, IntegerArgument start, IntegerArgument period) {
        this.generalParameters = generalParameters;
        this.id = id;
        this.start = start;
        this.period = period;
        this.layerManager = layerManager;
    }

    public IntegerArgument getPeriod() {
        return period;
    }

    public IntegerArgument getStart() {
        return start;
    }

    public int getId() {
        return id;
    }

    public GeneralParameters getGeneralParameters() {
        return generalParameters;
    }

    public LayerManager getLayerManager() {
        return layerManager;
    }


}
