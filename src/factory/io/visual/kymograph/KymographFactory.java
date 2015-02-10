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

package factory.io.visual.kymograph;

import control.GeneralParameters;
import factory.io.visual.color.ColorManagerFactory;
import factory.io.visual.highlight.HighlightManagerFactory;
import io.visual.VisualizationProperties;
import io.visual.color.ColorManager;
import io.visual.highlight.HighlightManager;
import io.visual.kymograph.Kymograph;
import org.dom4j.Element;
import structural.utilities.XmlUtil;

/**
 * Created by dbborens on 4/3/14.
 */
public abstract class KymographFactory {

    public static final int DEFAULT_EDGE = 10;

    // You often don't want a cell outline for a kymograph. Default to 0.
    public static final int DEFAULT_OUTLINE = 0;

    public static Kymograph instantiate(Element mapElement, GeneralParameters p) {
        // Make highlight manager
        HighlightManager highlightManager = makeHighlightManager(mapElement);

        // Make color manager
        ColorManager colorManager = makeColorManager(mapElement, p);

        // Get edge size
        int edge = getEdge(mapElement);

        // Get outline thickness
        int outline = getOutline(mapElement);

        if (outline != 0) {
            throw new UnsupportedOperationException("Outlines on kymographs are currently broken.");
        }
        // Construct map state object
        VisualizationProperties properties = makeProperties(highlightManager, colorManager, edge, outline);

        // Construct map
        Kymograph kymograph = new Kymograph(properties);

        return kymograph;
    }

    private static VisualizationProperties makeProperties(HighlightManager highlightManager,
                                                          ColorManager colorManager,
                                                          int edge, int outline) {

        VisualizationProperties properties = new VisualizationProperties(colorManager, edge, outline);
        properties.setHighlightManager(highlightManager);
        return properties;
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
