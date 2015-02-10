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

package factory.io.visual.map;

import control.GeneralParameters;
import factory.io.visual.color.ColorManagerFactory;
import factory.io.visual.highlight.HighlightManagerFactory;
import io.visual.VisualizationProperties;
import io.visual.color.ColorManager;
import io.visual.highlight.HighlightManager;
import io.visual.map.MapVisualization;
import org.dom4j.Element;
import structural.utilities.XmlUtil;

/**
 * Created by dbborens on 4/3/14.
 */
public abstract class MapVisualizationFactory {

    public static final int DEFAULT_EDGE = 10;
    public static final int DEFAULT_OUTLINE = 1;

    public static MapVisualization instantiate(Element mapElement, GeneralParameters p) {
        // Make highlight manager
        HighlightManager highlightManager = makeHighlightManager(mapElement);

        // Make color manager
        ColorManager colorManager = makeColorManager(mapElement, p);

        // Get edge size
        int edge = getEdge(mapElement);

        int outline = getOutline(mapElement);

        // Construct map state object
        VisualizationProperties visualizationProperties = makeVisualizationProperties(highlightManager, colorManager, edge, outline);

        // Construct map
        MapVisualization map = new MapVisualization(visualizationProperties);

        return map;
    }

    private static VisualizationProperties makeVisualizationProperties(HighlightManager highlightManager,
                                                                       ColorManager colorManager,
                                                                       int edge, int outline) {

        VisualizationProperties mapState = new VisualizationProperties(colorManager, edge, outline);
        mapState.setHighlightManager(highlightManager);
        return mapState;
    }

    private static HighlightManager makeHighlightManager(Element root) {
        Element highlightRoot = root.element("highlights");
        HighlightManager ret = HighlightManagerFactory.instantiate(highlightRoot);
        return ret;
    }

    private static ColorManager makeColorManager(Element root, GeneralParameters p) {
        Element colorRoot = root.element("color");
        ColorManager ret = ColorManagerFactory.instantiate(colorRoot, p);
        return ret;
    }

    private static int getEdge(Element root) {
        int edge = XmlUtil.getInteger(root, "edge", DEFAULT_EDGE);
        return edge;
    }

    private static int getOutline(Element root) {
        int outline = XmlUtil.getInteger(root, "outline", DEFAULT_OUTLINE);
        return outline;
    }
}
