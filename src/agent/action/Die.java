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

package agent.action;

import cells.BehaviorCell;
import control.arguments.Argument;
import control.halt.HaltCondition;
import control.identifiers.Coordinate;
import layers.LayerManager;

/**
 * Created by dbborens on 2/10/14.
 */
public class Die extends Action {

    private Argument<Integer> channel;

    public Die(BehaviorCell callback, LayerManager layerManager, Argument<Integer> channel) {
        super(callback, layerManager);
        this.channel = channel;
    }

    @Override
    public void run(Coordinate caller) throws HaltCondition {
        doHighlight(channel, getOwnLocation());
        getCallback().die();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Die) {
            return true;
        }
        return false;
    }

    @Override
    public Action clone(BehaviorCell child) {
        return new Die(child, getLayerManager(), channel);
    }
}
