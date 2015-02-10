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

package io.deserialize;


import control.identifiers.Coordinate;

import java.util.HashMap;

/**
 * This isn't really a mock coordinate de-indexer, so much as an in-memory
 * one. Eventually, it may get a promotion.
 * <p>
 * Created by David B Borenstein on 3/25/14.
 */
public class MockCoordinateDeindexer extends CoordinateDeindexer {
    private Coordinate[] underlying;

    public MockCoordinateDeindexer() {
        super();
    }

    public void setUnderlying(Coordinate[] underlying) {
        this.underlying = underlying;
        deindex();
    }


    @Override
    protected void deindex() {
        indexToCoord = new HashMap<>();
        coordToIndex = new HashMap<>();


        for (Integer i = 0; i < underlying.length; i++) {
            Coordinate c = underlying[i];
            indexToCoord.put(i, c);
            coordToIndex.put(c, i);
        }

    }
}
