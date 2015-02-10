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

package processes;

import control.GeneralParameters;
import control.arguments.Argument;
import layers.LayerManager;

/**
 * Created by dbborens on 11/23/14.
 */
public class BaseProcessArguments {
    private GeneralParameters generalParameters;
    private int id;
    private Argument<Integer> start;
    private Argument<Integer> period;

    private LayerManager layerManager;

    public BaseProcessArguments(LayerManager layerManager, GeneralParameters generalParameters, int id, Argument<Integer> start, Argument<Integer> period) {
        this.generalParameters = generalParameters;
        this.id = id;
        this.start = start;
        this.period = period;
        this.layerManager = layerManager;
    }

    public Argument<Integer> getPeriod() {
        return period;
    }

    public Argument<Integer> getStart() {
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
