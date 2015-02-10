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

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by dbborens on 12/31/14.
 */
public class ContinuumAgentLinker {

    private ContinuumAgentNotifier notifier;
    private Function<Coordinate, Double> stateLookup;

    public ContinuumAgentLinker(ContinuumAgentNotifier notifier,
                                Function<Coordinate, Double> stateLookup) {

        this.notifier = notifier;
        this.stateLookup = stateLookup;
    }

    public double get(Supplier<Coordinate> c) {
        return stateLookup.apply(c.get());
    }

    public ContinuumAgentNotifier getNotifier() {
        return notifier;
    }
}
