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

package nanoverse.runtime.layers;

import nanoverse.runtime.control.identifiers.*;

/**
 * Parent class for objects that can report read-only information on a system
 * state. This is not to be confused with LayerManager, which gives read-write
 * access to the system state. This could be used either for enforcing scope,
 * or for reading final simulation states (eg, from a serialized data structure).
 *
 * @see nanoverse.runtime.layers.LayerManager
 * <p>
 * Created by David B Borenstein on 3/23/14.
 */
public abstract class SystemState {

    /**
     * Retrieves a reconstructed LayerManager object, through which the state of
     * cell nanoverse.runtime.layers and solute nanoverse.runtime.layers can be accessed. Note that mutating this
     * AgentLayer object will have undefined results.
     *
     * @return
     */
    // TODO Get rid of the fake layer manager--make accessors direct like continuum values
    public abstract LayerManager getLayerManager();

    public abstract Extrema getContinuumExtrema(String id);

    public abstract double getContinuumValue(String id, Coordinate c);

    /**
     * Returns the system time associated with this state.
     */
    public abstract double getTime();

    /**
     * Returns the frame number associated with this state.
     *
     * @return
     */
    public abstract int getFrame();

    /**
     * Specifies whether a given site is highlighted on a given highlight
     * channel. (Highlight channels may specify different visual effects
     * in order to distinguish between events that took place simultaneously.)
     *
     * @param channel The highlight channel ID.
     * @param coord   The coordinate whose highlight status is to be checked.
     */
    public abstract boolean isHighlighted(int channel, Coordinate coord);

}
