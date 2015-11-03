/*
 * Nanoverse: a declarative agent-based modeling language for natural and
 * social science.
 *
 * Copyright (c) 2015 David Bruce Borenstein and Nanoverse, LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package nanoverse.runtime.io.visual.map;

import nanoverse.runtime.control.identifiers.Coordinate;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.highlight.HighlightManager;
import nanoverse.runtime.layers.SystemState;

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
