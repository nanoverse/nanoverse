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
package nanoverse.compiler.pipeline.instantiate.factory.io.visual.highlight;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.io.visual.highlight.BullseyeGlyph;

import java.awt.*;

public class BullseyeGlyphFactory implements Factory<BullseyeGlyph> {

    private final BullseyeGlyphFactoryHelper helper;

    private Color primary;
    private Color secondary;
    private double size;

    public BullseyeGlyphFactory() {
        helper = new BullseyeGlyphFactoryHelper();
    }

    public BullseyeGlyphFactory(BullseyeGlyphFactoryHelper helper) {
        this.helper = helper;
    }

    public void setPrimary(Color primary) {
        this.primary = primary;
    }

    public void setSecondary(Color secondary) {
        this.secondary = secondary;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public BullseyeGlyph build() {
        return helper.build(primary, secondary, size);
    }
}