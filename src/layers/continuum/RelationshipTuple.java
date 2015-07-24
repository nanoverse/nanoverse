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

/**
 * Created by dbborens on 12/30/14.
 */
public class RelationshipTuple {

    private final Coordinate coordinate;
    private final Reaction reaction;

    public RelationshipTuple(Coordinate coordinate, Reaction reaction) {
        this.coordinate = coordinate;
        this.reaction = reaction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getInj() {
        return reaction.getInj();
    }

    public double getExp() {
        return reaction.getExp();
    }

}
