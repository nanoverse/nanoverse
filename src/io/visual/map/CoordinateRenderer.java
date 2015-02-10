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

package io.visual.map;

import control.identifiers.Coordinate;
import io.visual.VisualizationProperties;
import io.visual.highlight.HighlightManager;
import layers.SystemState;

import java.awt.*;

/**
 * Created by dbborens on 4/1/14.
 */
public class CoordinateRenderer {

    private Graphics2D graphics;
    private PixelTranslator translator;
    private VisualizationProperties mapState;

    public CoordinateRenderer(Graphics2D graphics, PixelTranslator translator, VisualizationProperties mapState) {
        this.graphics = graphics;
        this.translator = translator;
        this.mapState = mapState;
    }

    public void render(Coordinate c, SystemState systemState) {
        if (!translator.isRetained(c)) {
            return;
        }

        renderFill(c, systemState);

        if (mapState.getOutline() != 0) {
            renderOutline(c, systemState);
        }

        renderHighlights(c, systemState);
    }

    private void renderFill(Coordinate toRender, SystemState systemState) {
        Color color = mapState.getColorManager().getColor(toRender, systemState);
        Polygon p = translator.makePolygon(toRender, systemState.getFrame(), systemState.getTime());

        // Flood region with background color
        graphics.setColor(color);
        graphics.fillPolygon(p);
    }

    private void renderOutline(Coordinate toRender, SystemState systemState) {
        Color color = mapState.getColorManager().getBorderColor();
        Polygon p = translator.makePolygon(toRender, systemState.getFrame(), systemState.getTime());

        // Flood region with background color
        graphics.setColor(color);
        graphics.drawPolygon(p);
    }

    private void renderHighlights(Coordinate toRender, SystemState systemState) {
        HighlightManager highlightManager = mapState.getHighlightManager();
        highlightManager.render(toRender, systemState);
    }

}
