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

package nanoverse.runtime.io.visual.highlight;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.map.PixelTranslator;

import java.awt.*;

/**
 * A glyph is an overlay onto a lattice cell. It is used for highlighting in
 * the context of eSLIME visualizations. Each subclass defines a glyph that will
 * be used in a particular visualization. The overlay() method then puts this
 * glyph onto a particular site.
 * <p>
 * Created by dbborens on 4/1/14.
 */
public abstract class Glyph {

    // The pixel translator converts coordinates to pixels.
    protected PixelTranslator translator;

    protected Graphics2D graphics;

    public void init(PixelTranslator translator) {
        this.translator = translator;
        internalInit();
    }

    /**
     * Set the Graphics object to which the glyphs should be written.
     */
    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }

    /**
     * Draw the glyph at the specified site.
     *
     * @param c Coordinate (in units of nanoverse.runtime.cells) of the site to be overlaid
     *          with the image.
     */
    public abstract void overlay(Coordinate c, int frame, double time);

    @Override
    public abstract boolean equals(Object obj);

    protected abstract void internalInit();

    protected int calcProportionalSize(double size) {
        // The length of a diagonal of the cell polygon.
        double diagonal = translator.getDiagonal();

        double scaled = diagonal * size / 2.0;
        return (int) scaled;
    }
}
