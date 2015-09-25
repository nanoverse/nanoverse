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

package nanoverse.runtime.io.visual.glyph;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.highlight.Glyph;

import java.awt.*;

/**
 * Created by dbborens on 4/2/14.
 */
public class MockGlyph extends Glyph {
    private Graphics2D graphics;
    private Coordinate lastOverlaid;

    public MockGlyph() {
        lastOverlaid = null;
    }

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }

    @Override
    public void overlay(Coordinate c, int frame, double time) {
        this.lastOverlaid = c;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MockGlyph);
    }

    @Override
    protected void internalInit() {
    }

    public Coordinate getLastOverlaid() {
        return lastOverlaid;
    }
}
