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

package compiler.pipeline.instantiate.loader.io.visual;

import compiler.pipeline.instantiate.factory.io.visual.VisualizationPropertiesFactory;
import compiler.pipeline.instantiate.loader.Loader;
import compiler.pipeline.translate.nodes.MapObjectNode;
import control.GeneralParameters;
import io.visual.VisualizationProperties;
import io.visual.color.ColorManager;
import io.visual.highlight.HighlightManager;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by dbborens on 8/21/2015.
 */
public class VisualizationPropertiesLoader
    extends Loader<VisualizationProperties> {

    private final VisualizationPropertiesFactory factory;
    private final VisualizationPropertiesInterpolator interpolator;

    public VisualizationPropertiesLoader() {
        interpolator = new VisualizationPropertiesInterpolator();
        factory = new VisualizationPropertiesFactory();
    }

    public VisualizationPropertiesLoader(VisualizationPropertiesFactory factory,
                                         VisualizationPropertiesInterpolator interpolator) {

        this.factory = factory;
        this.interpolator = interpolator;
    }

    public VisualizationProperties instantiate(MapObjectNode node, GeneralParameters p) {
        configureFactory(node, p);
        return factory.build();
    }

    public VisualizationProperties instantiate(GeneralParameters p,
                                               Stream<Consumer<VisualizationPropertiesFactory>> overrides) {
        configureFactory(null, p);
        overrides.forEach(override -> override.accept(factory));
        return factory.build();
    }

    private void configureFactory(MapObjectNode node, GeneralParameters p) {
        Integer edge = interpolator.edge(node, p.getRandom());
        factory.setEdge(edge);

        Integer outline = interpolator.outline(node, p.getRandom());
        factory.setOutline(outline);

        ColorManager colorModel = interpolator.colorModel(node, p);
        factory.setColorManager(colorModel);

        HighlightManager highlight = interpolator.highlights(node, p);
        factory.setHighlightManager(highlight);
    }
}
