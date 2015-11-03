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
package nanoverse.compiler.pipeline.instantiate.factory.io.visual;

import nanoverse.compiler.pipeline.instantiate.factory.Factory;
import nanoverse.runtime.io.visual.VisualizationProperties;
import nanoverse.runtime.io.visual.color.ColorManager;
import nanoverse.runtime.io.visual.highlight.HighlightManager;

public class VisualizationPropertiesFactory implements Factory<VisualizationProperties> {

    private final VisualizationPropertiesFactoryHelper helper;

    private ColorManager colorManager;
    private int edge;
    private int outline;
    private HighlightManager highlightManager;

    public VisualizationPropertiesFactory() {
        helper = new VisualizationPropertiesFactoryHelper();
    }

    public VisualizationPropertiesFactory(VisualizationPropertiesFactoryHelper helper) {
        this.helper = helper;
    }

    public void setColorManager(ColorManager colorManager) {
        this.colorManager = colorManager;
    }

    public void setEdge(int edge) {
        this.edge = edge;
    }

    public void setOutline(int outline) {
        this.outline = outline;
    }

    public void setHighlightManager(HighlightManager highlightManager) {
        this.highlightManager = highlightManager;
    }

    @Override
    public VisualizationProperties build() {
        return helper.build(colorManager, edge, outline, highlightManager);
    }
}